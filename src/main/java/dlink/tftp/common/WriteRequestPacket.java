package dlink.tftp.common;

import dlink.tftp.util.Mode;
import dlink.tftp.util.TFTPException;

/**
 * Created by Chenlv on 2018.5.29.
 * WRQ包
 */
public class WriteRequestPacket extends RequestPacket {

    /**
     * {@inheritDoc}
     */
    public WriteRequestPacket(String file, Mode mode) {
        super(file, mode);
    }

    /**
     * {@inheritDoc}
     */
    public WriteRequestPacket(byte[] bytes, int length) throws TFTPException{
        super(bytes, length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PacketType getPacketType() {
        return PacketType.WRITE_REQUEST;
    }

}
