package dlink.tftp.common;

import dlink.tftp.util.Mode;
import dlink.tftp.util.TFTPException;
import dlink.tftp.util.StringUtil;

import java.nio.ByteBuffer;

/**
 * Created by Chenlv on 2018.5.29.
 * 继承自通用TFTP包
 * 因为RRQ包和WRQ包只是包类型不一样，其它都一样
 */
public abstract class RequestPacket extends TFTPPacket {

    /**
     * 文件名（带路径）
     */
    private final String fileName;

    /**
     * TFTP传输模式
     */
    private final Mode mode;

    /**
     * 数据包字节
     */
    private final byte[] bytes;

    /**
     * 创建新的请求包
     *
     * @param fileName 文件名
     * @param mode 传输模式
     */
    public RequestPacket(String fileName, Mode mode) {
        this.fileName = fileName;
        this.mode = mode;

        byte[] fileNameBytes = StringUtil.getBytes(fileName);
        byte[] modeBytes = StringUtil.getBytes(mode.getName());
        this.bytes = new byte[fileNameBytes.length + modeBytes.length + 2];

        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.putShort(getPacketType().getOpcode());
        buffer.put(fileNameBytes);
        buffer.put(modeBytes);
    }

    /**
     * 从原始分组字节创建新的请求包
     *
     * @param bytes 数据包的缓冲区
     * @param length 数据包的长度
     * @throws TFTPException
     */
    public RequestPacket(byte[] bytes, int length) throws TFTPException {
        this.fileName = StringUtil.getString(bytes, 2);
        //we found the file-name string already (starting at offset 2). now need to find start of mode
        // string - so increment a counter until the null byte indicating the end of the filename is found,
        // then the mode string starts at the offset immediately after the null byte
        int modeStringOffset = 2;
        String s=bytes.toString();
        while (bytes[modeStringOffset] != 0 && modeStringOffset < length) {
            ++modeStringOffset;
        }
        ++modeStringOffset;

        this.mode = Mode.fromName(StringUtil.getString(bytes, modeStringOffset));
        this.bytes = new byte[length];
        System.arraycopy(bytes, 0, this.bytes, 0, length);
    }

    /**
     * @return 此包中的文件名
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @return 此包中的传输模式
     */
    public Mode getMode() {
        return mode;
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
    public String toString() {
        return String.format("%s[file=%s,mode=%s]", getPacketType(), getFileName(), getMode());
    }

}
