package dlink.tftp.util;

import dlink.tftp.server.FileReceiver;
import dlink.tftp.common.TFTPPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;

/**
 * Created by 91680 on 2018.6.1.
 * 统计并分析接收的包
 */
public class packageStatistics {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileReceiver.class);
    private static int allBlockNumber;
    public static void setAllBlockNumber(String fileName) throws IOException {
        byte[] fileBuffer = new byte[TftpConfiguration.getMax_data_length()];
        FileInputStream fis = new FileInputStream(TftpConfiguration.getCurrent_directory()+fileName);
        while (fis.read(fileBuffer)!=-1){
            allBlockNumber++;
        }

    }
    public static boolean processReceives(DatagramPacket rcvDatagram,int blockNumber,int blockErrNumber) throws TFTPException{
        TFTPPacket packet = UDPUtil.fromDatagram(rcvDatagram);
        if(allBlockNumber==blockNumber){
            blockNumber=0;
        }
        switch (packet.getPacketType()) {
            case ACKNOWLEDGEMENT:
                LOGGER.info("send success "+blockNumber+" package,and a total of "+allBlockNumber+" packages.");
                if(blockNumber==allBlockNumber)
                    LOGGER.info("All packets are sent to success.");
                return true;
            case ERROR:
                blockErrNumber++;
                LOGGER.info("send failure "+blockErrNumber +" package,and a total of "+allBlockNumber+" packages.");
                return false;
            default:
                LOGGER.info("received packet " + packet + ", ignoring");
                return false;
        }

    }
    public static boolean processSents(DatagramPacket rcvDatagram,int blockNumber,int blockErrNumber) throws TFTPException{
        TFTPPacket packet = UDPUtil.fromDatagram(rcvDatagram);
        switch (packet.getPacketType()) {
            case ACKNOWLEDGEMENT:
                LOGGER.info("send success"+blockNumber+" package");
                return true;
            case ERROR:
                blockErrNumber++;
                LOGGER.info("send failure"+blockErrNumber +" package");
                return false;
            default:
                LOGGER.info("received packet " + packet + ", ignoring");
                return false;
        }
    }
}
