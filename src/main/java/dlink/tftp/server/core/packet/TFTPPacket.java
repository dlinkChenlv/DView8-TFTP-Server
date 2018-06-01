package dlink.tftp.server.core.packet;

import dlink.tftp.server.core.util.TFTPException;
import java.nio.ByteBuffer;

/**
 * Created by Chenlv on 2018.5.29.
 * 通用TFTP包 抽象类
 */
public abstract class TFTPPacket {

    /**
     * 数据
     *
     * @return the raw packet bytes
     */
    public abstract byte[] getPacketBytes();

    /**
     * @return 包类型(例如 ACK, RRQ, ...)
     */
    public abstract PacketType getPacketType();

    /**
     * 给定一个字节数组（和一个长度），它检查操作码，然后根据包类型解析包
     *
     * @param buffer the buffer holding the TFTP packet bytes
     * @param length the length (in bytes) of the TFTP packet
     * @return a TFTPPacket with attributes described in the given byte array
     * @throws TFTPException if the packet type is unknown, or if the bytes could not be parsed correctly
     */
    public static TFTPPacket fromByteArray(byte[] buffer, int length) throws TFTPException {
        short opcode = ByteBuffer.wrap(buffer).getShort();
        PacketType type = PacketType.fromOpcode(opcode);

        switch (type) {
            case ACKNOWLEDGEMENT:
                return new AcknowledgementPacket(buffer, length);
            case DATA:
                return new DataPacket(buffer, length);
            case ERROR:
                return new ErrorPacket(buffer, length);
            case READ_REQUEST:
                return new ReadRequestPacket(buffer, length);
            case WRITE_REQUEST:
                return new WriteRequestPacket(buffer, length);
            default:
                throw new TFTPException("unknown packet type: " + type);
        }

    }

}
