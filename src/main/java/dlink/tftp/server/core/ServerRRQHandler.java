package dlink.tftp.server.core;

import dlink.tftp.server.core.util.*;
import dlink.tftp.server.core.packet.ReadRequestPacket;
import dlink.tftp.server.core.packet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by chenlv on 2018.5.28.
 * RRQ 服务线程
 */
public class ServerRRQHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ServerRRQHandler.class);
    private final InetAddress clientAddress;//客户端ip
    private final int clientPort;//客户端端口
    private final ReadRequestPacket rrq;//rrq请求

    public ServerRRQHandler(InetAddress clientAddress, int clientPort, ReadRequestPacket rrq) {
        this.clientAddress = clientAddress;
        this.clientPort = clientPort;
        this.rrq = rrq;
    }

    @Override
    public void run() {
        logger.info("responding to request: " + rrq + " from client: " + clientAddress + ":" + clientPort);
        try {
            DatagramSocket socket = new DatagramSocket();
            socket.setSoTimeout(TftpConfiguration.getTimeout());//设置超时重发时间
//            if (rrq.getMode() != Mode.OCTET) {
//                ErrorPacket error = new ErrorPacket(ErrorType.UNDEFINED, "unsupported mode: " + rrq.getMode());
//                socket.send(UDPUtil.toDatagram(error, clientAddress, clientPort));
//                System.out.println("unsupported mode: " + rrq.getMode());
//                return;
//            }
            try (FileInputStream fis = new FileInputStream(TftpConfiguration.getCurrent_directory()+rrq.getFileName())) {
                //上传时同时生成临时文件，用Md5校验后删除
                FileOutputStream ois = new FileOutputStream(TftpConfiguration.getCurrent_directory()+rrq.getFileName()+"_tmp");
                byte[] first = new byte[TftpConfiguration.getMax_data_length()];
                int read = fis.read(first);
                if (read == -1) read = 0;
                DataPacket data = new DataPacket((short) 1, first, read);
                ois.write(first,0,read);
                //统计发送包数
                packageStatistics.setAllBlockNumber(rrq.getFileName());
                //开始发送包
                FileSender.send(socket, data, clientAddress, clientPort, fis,ois, (short) 1);
                //校验发送文件并删除
                Boolean checkres= Md5Util.CheckSendFile(rrq.getFileName());
                logger.info("file check result is " + checkres);
            } catch (FileNotFoundException e) {
                ErrorPacket errorPacket = new ErrorPacket(
                        ErrorType.FILE_NOT_FOUND,
                        "file not found: " + rrq.getFileName()
                );
                DatagramPacket sendPacket = UDPUtil.toDatagram(errorPacket, clientAddress, clientPort);
                socket.send(sendPacket);
            } catch (TFTPException e) {
                logger.error(e.getMessage());
            }
        } catch (IOException e) {
            logger.error("error: " + e.getMessage());
        }
    }
}
