package dlink.tftp.server;

import dlink.tftp.common.AcknowledgementPacket;
import dlink.tftp.common.DataPacket;
import dlink.tftp.common.ErrorPacket;
import dlink.tftp.util.TftpConfiguration;
import dlink.tftp.common.TFTPPacket;
import dlink.tftp.util.TFTPException;
import dlink.tftp.util.UDPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

/**
 * Created by chenlv on 2018.5.28.
 * 接收文件包
 */
public class FileReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileReceiver.class);
    /**
     * 从TFTP主机接收文件
     *
     * @param socket
     * @param firstPacket
     * @param remoteAddress
     * @param remotePort
     * @param fos
     * @throws TFTPException
     */
    public static void receive(
            DatagramSocket socket, TFTPPacket firstPacket, InetAddress remoteAddress,
            int remotePort, FileOutputStream fos) throws TFTPException {
        long startTime = System.currentTimeMillis();
        int bytesReceived = 0;
        TFTPPacket sendPacket;
        byte[] rcvBuffer = new byte[TftpConfiguration.getMax_packet_length()];
        DatagramPacket rcvDatagram = new DatagramPacket(rcvBuffer, rcvBuffer.length);
        boolean first = true;
        short ackNumber = 0;
        while (true) {
            if (first) {
                sendPacket = firstPacket;
            } else {
                sendPacket = new AcknowledgementPacket(ackNumber);
            }
            DatagramPacket datagram = UDPUtil.toDatagram(sendPacket, remoteAddress, remotePort);
            int timeouts = 0;
            int invalids = 0;
            while (timeouts < TftpConfiguration.getMax_timeouts() && invalids < TftpConfiguration.getMax_invalids()) {
                try {
                    socket.send(datagram);
                    try {
                        socket.receive(rcvDatagram);
                    } catch (SocketTimeoutException timeout) {
                        LOGGER.error("timed out, resending " + sendPacket);
                        ++timeouts;
                        continue;
                    }
                    if (ackNumber == 0) {
                        remotePort = rcvDatagram.getPort();
                    }
                    TFTPPacket packet;
                    try {
                        packet = UDPUtil.fromDatagram(rcvDatagram);
                    } catch (TFTPException e) {
                        ++invalids;
                        continue;
                    }
                    if (packet instanceof DataPacket) {
                        DataPacket data = (DataPacket) packet;
                        if (data.getBlockNumber() == (short) (ackNumber + 1)) {
                            fos.write(data.getPacketBytes(), DataPacket.DATA_OFFSET, data.getDataLength());
                            bytesReceived += data.getDataLength();
                            ++ackNumber;
                            first = false;
                            if (data.isFinalPacket()) {
                                sendPacket = new AcknowledgementPacket(ackNumber);
                                datagram = UDPUtil.toDatagram(sendPacket, remoteAddress, remotePort);
                                socket.send(datagram);
                                long time = System.currentTimeMillis() - startTime;
                                double seconds = (double) time / 1000.0;
                                BigDecimal bigDecimal = new BigDecimal(seconds);
                                bigDecimal = bigDecimal.setScale(1, BigDecimal.ROUND_UP);
                                LOGGER.info("received "+bytesReceived+" bytes in "+bigDecimal.toPlainString()+" seconds.");
                                return;
                            }
                            break;
                        }
                    } else if (packet instanceof ErrorPacket) {
                        LOGGER.error("error: " + ((ErrorPacket) packet).getMessage());
                        return;
                    }
                } catch (IOException e) {
                    ++invalids;
                }
            }

            if (timeouts == TftpConfiguration.getMax_timeouts()) {
                throw new TFTPException("error: transfer timed out");
            } else if (invalids == TftpConfiguration.getMax_invalids()) {
                throw new TFTPException(
                        "error: too many invalid packets received " +
                        "or failed to write to file too many times"
                );
            }
        }
    }

}
