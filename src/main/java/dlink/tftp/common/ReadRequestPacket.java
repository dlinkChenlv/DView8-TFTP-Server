package dlink.tftp.common;

import dlink.tftp.util.Mode;
import dlink.tftp.util.TFTPException;

/**
 * Created by Chenlv on 2018.5.29.
 * RRQ包
 */
public class ReadRequestPacket extends RequestPacket {

    /**
     * {@inheritDoc}
     */
    public ReadRequestPacket(String file, Mode mode) {
        super(file, mode);
    }

    /**
     * {@inheritDoc}
     */
    public ReadRequestPacket(byte[] bytes, int length) throws TFTPException{
        super(bytes, length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PacketType getPacketType() {
        return PacketType.READ_REQUEST;
    }

}
