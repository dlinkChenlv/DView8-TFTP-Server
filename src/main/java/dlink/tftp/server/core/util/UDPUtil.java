package dlink.tftp.server.core.util;

import dlink.tftp.server.core.util.TFTPException;
import dlink.tftp.server.core.packet.TFTPPacket;

import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * Created by ChenLv on 2018.5.25.
 * 提供与数据报中发送/接收TFTP分组相关的服务的公用事业类
 */
public class UDPUtil {

    /**
     *
     * @param datagram
     * @return TFTPPacket
     * @throws TFTPException
     */
    public static TFTPPacket fromDatagram(DatagramPacket datagram) throws TFTPException {
        return TFTPPacket.fromByteArray(datagram.getData(), datagram.getLength());
    }

    /**
     *
     * @param packet
     * @param address
     * @param port
     * @return DatagramPacket
     */
    public static DatagramPacket toDatagram(TFTPPacket packet, InetAddress address, int port) {
        byte[] data = packet.getPacketBytes();
        DatagramPacket datagram = new DatagramPacket(data, 0, data.length);
        datagram.setAddress(address);
        datagram.setPort(port);
        return datagram;
    }

}
