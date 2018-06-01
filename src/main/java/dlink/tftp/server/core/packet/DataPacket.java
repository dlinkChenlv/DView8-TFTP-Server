package dlink.tftp.server.core.packet;

import dlink.tftp.server.core.util.TftpConfiguration;

import java.nio.ByteBuffer;

/**
 * Created by Chenlv on 2018.5.29.
 * DATA包
 */
public class DataPacket extends TFTPPacket {

    /**
     * 数据包的偏移位
     */
    public static final int DATA_OFFSET = 4;

    /**
     * 数据块序号
     */
    private final short blockNumber;

    /**
     * 数据长度 单位字节
     */
    private final int dataLength;

    /**
     *  数据
     */
    private final byte[] packetBuffer;

    /**
     *创建一个新的数据包
     *
     * @param blockNumber 数据块序号
     * @param dataBuffer 发送的数据缓存区
     * @param dataLength 数据缓存区长度
     */
    public DataPacket(short blockNumber, byte[] dataBuffer, int dataLength) {
        this.blockNumber = blockNumber;
        this.dataLength = dataLength;
        this.packetBuffer = new byte[dataLength + DATA_OFFSET];
        ByteBuffer buffer = ByteBuffer.wrap(packetBuffer);
        buffer.putShort(getPacketType().getOpcode());
        buffer.putShort(blockNumber);
        buffer.put(dataBuffer, 0, dataLength);
    }

    /**
     * 从原始TFTP包检索数据包实例。
     *
     * @param packetBuffer 包含分组数据的缓冲区
     * @param length 数据缓存区长度
     */
    public DataPacket(byte[] packetBuffer, int length) {
        ByteBuffer buffer = ByteBuffer.wrap(packetBuffer);
        buffer.position(2);
        this.blockNumber = buffer.getShort();
        this.dataLength = length - DATA_OFFSET;

        this.packetBuffer = new byte[length];
        System.arraycopy(packetBuffer, 0, this.packetBuffer, 0, length);
    }

    /**
     * @return 数据块序号
     */
    public short getBlockNumber() {
        return blockNumber;
    }

    /**
     * @return 数据长度 单位字节（除最后一个包，都是512）
     */
    public int getDataLength() {
        return dataLength;
    }

    /**
     *
     * @return 是否是最后一个数据包
     */
    public boolean isFinalPacket() {
        return dataLength < TftpConfiguration.getMax_data_length();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] getPacketBytes() {
        return packetBuffer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PacketType getPacketType() {
        return PacketType.DATA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%s[block=%d,length=%d]", getPacketType(), getBlockNumber(), dataLength);
    }

}
