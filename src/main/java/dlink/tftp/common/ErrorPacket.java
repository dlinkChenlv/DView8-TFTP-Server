package dlink.tftp.common;

import dlink.tftp.util.ErrorType;
import dlink.tftp.util.StringUtil;

import java.nio.ByteBuffer;

/**
 * Created by Chenlv on 2018.5.29.
 * ERROR包
 */
public class ErrorPacket extends TFTPPacket {

    /**
     * 错误类型
     */
    private final ErrorType errorType;

    /**
     * 错误信息
     */
    private final String message;

    /**
     * 数据包字节
     */
    private final byte[] bytes;

    /**
     * 新建一个错误包
     *
     * @param errorType 错误类型
     * @param message 错误信息
     */
    public ErrorPacket(ErrorType errorType, String message) {
        this.errorType = errorType;
        this.message = message;

        byte[] messageBytes = StringUtil.getBytes(message);
        this.bytes = new byte[messageBytes.length + 4];

        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.putShort(getPacketType().getOpcode());
        buffer.putShort(errorType.getValue());
        buffer.put(messageBytes);
    }

    /**
     * 从原始分组字节检索错误分组
     *
     * @param bytes 包含数据包字节的缓冲区
     * @param length 数据包长度
     */
    public ErrorPacket(byte[] bytes, int length) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.position(2);
        this.errorType = ErrorType.fromValue(buffer.getShort());
        this.message = StringUtil.getString(bytes, 4);
        this.bytes = new byte[length];
        System.arraycopy(bytes, 0, this.bytes, 0, length);
    }

    /**
     * @return 错误类型
     */
    public ErrorType getErrorType() {
        return errorType;
    }

    /**
     * @return 错误信息，一般包含有关错误的特定信息
     */
    public String getMessage() {
        return message;
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
        return PacketType.ERROR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%s[code=%d,message=%s]", getPacketType(), errorType.getValue(), message);
    }

}
