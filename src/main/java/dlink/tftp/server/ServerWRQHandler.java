package dlink.tftp.server;

import dlink.tftp.common.AcknowledgementPacket;
import dlink.tftp.common.ErrorPacket;
import dlink.tftp.common.WriteRequestPacket;
import dlink.tftp.util.TftpConfiguration;
import dlink.tftp.util.ErrorType;
import dlink.tftp.util.TFTPException;
import dlink.tftp.util.UDPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by chenlv on 2018.5.28.
 * WRQ 服务线程
 */
public class ServerWRQHandler implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerWRQHandler.class);
    private InetAddress clientAddress;//客户端ip
    private int clientPort;//客户端port
    private final WriteRequestPacket wrq;//wrq请求

    /**
     * 构造函数
     *
     * @param clientAddress  WRQ客户端ip
     * @param clientPort WRQ客户端port
     * @param wrq
     */
    public ServerWRQHandler(InetAddress clientAddress, int clientPort, WriteRequestPacket wrq) {
        this.clientAddress = clientAddress;
        this.clientPort = clientPort;
        this.wrq = wrq;
    }

    /**
     *发送WRQ
     */
    @Override
    public void run() {
        LOGGER.info("responding to request: " + wrq + " from client: " + clientAddress + ":" + clientPort);
        try {
            DatagramSocket socket = new DatagramSocket();
            socket.setSoTimeout(TftpConfiguration.getTimeout());
//            if (wrq.getMode() != Mode.OCTET) {
//                ErrorPacket error = new ErrorPacket(ErrorType.UNDEFINED, "unsupported mode: " + wrq.getMode());
//                socket.send(UDPUtil.toDatagram(error, clientAddress, clientPort));
//                System.out.println("unsupported mode: " + wrq.getMode());
//                return;
//            }
            try (FileOutputStream fos = new FileOutputStream(TftpConfiguration.getCurrent_directory()+wrq.getFileName())) {
                FileReceiver.receive(
                        socket,
                        new AcknowledgementPacket((short) 0),
                        clientAddress,
                        clientPort,
                        fos
                );
            } catch (FileNotFoundException fnfe) {
                LOGGER.info("unable to write to: " + wrq.getFileName());
                ErrorPacket errorPacket = new ErrorPacket(
                        ErrorType.FILE_NOT_FOUND,
                        "unable to write to: " + wrq.getFileName()
                );
                DatagramPacket datagram = UDPUtil.toDatagram(errorPacket, clientAddress, clientPort);
                socket.send(datagram);
            } catch (TFTPException e) {
                LOGGER.error(e.getMessage());
            }
        } catch (IOException e) {
            LOGGER.error("failed to receive: " + e.getMessage());
        }
    }
}
