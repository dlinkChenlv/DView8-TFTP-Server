package dlink.tftp.server.core;

import dlink.tftp.server.core.util.TftpConfiguration;
import dlink.tftp.server.core.util.TFTPException;
import dlink.tftp.server.core.packet.ReadRequestPacket;
import dlink.tftp.server.core.packet.TFTPPacket;
import dlink.tftp.server.core.packet.WriteRequestPacket;
import dlink.tftp.server.core.util.UDPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenlv on 2018.5.28.
 * TFTP启动线程
 */
public class TFTPUDPServer extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(TFTPUDPServer.class);
    private final int port;//tfp服务端口
    private final ExecutorService executor;//java的线程池
    public TFTPUDPServer(int port) {
        this.port = port;
        this.executor = Executors.newCachedThreadPool();
    }

    //TFTPUDPServer线程的run
    @Override
    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket(port);//UDP协议的Socket 这是和使用TCP发送数据包的不同之处
            byte[] buffer = new byte[TftpConfiguration.getMax_packet_length()];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            //持续接收client处的消息
            while (true) {
                try {
                    //未收到消息一直等待
                    socket.receive(receivePacket);
                } catch (IOException e) {
                    logger.error("error receiving packet: " + e);
                    continue;
                }
                try {
                    //整理接收到的数据包
                    TFTPPacket packet = UDPUtil.fromDatagram(receivePacket);
                    //调线程池 处理RRQ或者WRQ
                    switch (packet.getPacketType()) {
                        case READ_REQUEST:
                            executor.submit(new ServerRRQHandler(
                                    receivePacket.getAddress(),
                                    receivePacket.getPort(),
                                    (ReadRequestPacket) packet
                            ));
                            break;
                        case WRITE_REQUEST:
                            executor.submit(new ServerWRQHandler(
                                    receivePacket.getAddress(),
                                    receivePacket.getPort(),
                                    (WriteRequestPacket) packet
                            ));
                            break;
                        default:
                            logger.info("received packet " + packet + ", ignoring");
                            break;
                    }
                } catch (TFTPException e) {
                    logger.error("error parsing received packet: " + e);
                }
            }
        } catch (SocketException e) {
            logger.error("failed to start server: " + e);
        }
    }

}
