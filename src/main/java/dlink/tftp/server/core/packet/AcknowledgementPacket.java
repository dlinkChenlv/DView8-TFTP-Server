package dlink.tftp.server.core.packet;

import java.nio.ByteBuffer;
/**
 * Created by Chenlv on 2018.5.29.
 * ACK包
 */

public class AcknowledgementPacket extends TFTPPacket {

    /**
     * ACK包的固定长度
     */
    private static final int PACKET_LENGTH = 4;

    /**
     * 数据块序号
     */
    private final short blockNumber;

    /**
     * TFTP分组字节
     */
    private final byte[] bytes;

    /**
     *应答DATA时数据块由1开始依次往上累加以确认数据包是否有漏传
     * @param blockNumber
     */
    public AcknowledgementPacket(short blockNumber) {
        this.blockNumber = blockNumber;
        this.bytes = new byte[PACKET_LENGTH];

        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.putShort(getPacketType().getOpcode());
        buffer.putShort(blockNumber);
    }

    /**
     *新建ACK包
     *应答WRQ时使用的数据块序号为0
     * @param packetData the buffer holding the packet bytes
     * @param length   4
     */
    public AcknowledgementPacket(byte[] packetData, int length) {
        ByteBuffer buffer = ByteBuffer.wrap(packetData);
        buffer.position(2);
        this.blockNumber = buffer.getShort();
        this.bytes = new byte[length];
        System.arraycopy(packetData, 0, bytes, 0, length);
    }

    /**
     * {@inheritDoc}
     */
    public int getBlockNumber() {
        return blockNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] getPacketBytes() {
        return bytes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PacketType getPacketType() {
        return PacketType.ACKNOWLEDGEMENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%s{block=%d}", getPacketType(), getBlockNumber());
    }

}
