package dlink.tftp.server;

import dlink.tftp.common.*;
import dlink.tftp.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

/**
 * Created by chenlv on 2018.5.28.
 * 发送文件包
 */
public class FileSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileSender.class);
    /**
     * 发送包
     *
     * @param socket
     * @param firstPacket
     * @param remoteAddress
     * @param remotePort
     * @param fis
     * @param ois
     * @param firstBlockNumber
     * @throws TFTPException
     */
    public static void send(DatagramSocket socket, TFTPPacket firstPacket, InetAddress remoteAddress,
                            int remotePort, FileInputStream fis, FileOutputStream ois, short firstBlockNumber) throws TFTPException {
        long startTime = System.currentTimeMillis();
        int bytesSent = 0;
        TFTPPacket sendPacket;
        byte[] receiveBuffer = new byte[TftpConfiguration.getMax_packet_length()];
        byte[] fileBuffer = new byte[TftpConfiguration.getMax_data_length()];
        boolean first = true;
        short blockNumber = firstBlockNumber;
        int blockErrNumber=0;
        int read;
        int lastLength = TftpConfiguration.getMax_data_length();
        while (true) {
            if (first) {
                sendPacket = firstPacket;
                if (firstPacket instanceof DataPacket) {
                    lastLength = ((DataPacket) firstPacket).getDataLength();
                }
            } else {
                try {
                    read = fis.read(fileBuffer);
                } catch (IOException e) {
                    LOGGER.error("error reading from file");
                    return;
                }
                if (read == -1) {
                    if (lastLength == TftpConfiguration.getMax_data_length()) {
                        read = 0;
                    } else {
                        break;
                    }
                }
                sendPacket = new DataPacket(blockNumber, fileBuffer, read);
                lastLength = read;
            }
            DatagramPacket datagram = UDPUtil.toDatagram(sendPacket, remoteAddress, remotePort);
            DatagramPacket rcvDatagram = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            int timeouts = 0;
            int invalids = 0;
            while (timeouts < TftpConfiguration.getMax_timeouts() && invalids < TftpConfiguration.getMax_invalids()) {
                try {
                    socket.send(datagram);
                    try {
                        socket.receive(rcvDatagram);
                        //统计处理每次上传的包
                        if(packageStatistics.processReceives(rcvDatagram,blockNumber,blockErrNumber)){
                            //上传时同时生成临时文件，用Md5校验后删除
                            if(!first){
                                ois.write(fileBuffer,0,lastLength);
                            }
                        }else{
                            blockErrNumber++;
                        }
                    } catch (SocketTimeoutException timeout) {
                        LOGGER.error("timed out, resending " + sendPacket);
                        ++timeouts;
                        continue;
                    }
                    if (blockNumber == firstBlockNumber) {
                        remotePort = rcvDatagram.getPort();
                    }
                    TFTPPacket received;
                    try {
                        received = UDPUtil.fromDatagram(rcvDatagram);
                    } catch (TFTPException e) {
                        ++invalids;
                        continue;
                    }
                    if (received instanceof AcknowledgementPacket) {
                        AcknowledgementPacket ack = (AcknowledgementPacket) received;
                        if (ack.getBlockNumber() == blockNumber) {
                            if (sendPacket.getPacketType() == PacketType.DATA) {
                                bytesSent += ((DataPacket) sendPacket).getDataLength();
                            }
                            ++blockNumber;
                            first = false;
                            break;
                        }
                    } else if (received instanceof ErrorPacket) {
                        LOGGER.error("error: " + ((ErrorPacket) received).getMessage());
                        return;
                    }
                } catch (IOException e) {
                    ++invalids;
                }
            }
            if (timeouts == TftpConfiguration.getMax_timeouts()) {
                throw new TFTPException("error: transfer timed out");
            } else if (invalids == TftpConfiguration.getMax_invalids()) {
                throw new TFTPException("error: too many invalid packets received or error writing to/reading from socket");
            }
        }
        long time = System.currentTimeMillis() - startTime;
        double seconds = (double) time / 1000.0;
        BigDecimal bigDecimal = new BigDecimal(seconds);
        bigDecimal = bigDecimal.setScale(1, BigDecimal.ROUND_UP);
        LOGGER.info("sent "+bytesSent+" bytes in "+ bigDecimal.toPlainString()+" seconds");
    }
}
