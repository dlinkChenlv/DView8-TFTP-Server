package dlink.tftp.server.core.packet;

import dlink.tftp.server.core.util.TFTPException;

/**
 * Created by Chenlv on 2018.5.29.
 * 包分组
 */
public enum PacketType {
    READ_REQUEST("RRQ", 1),
    WRITE_REQUEST("WRQ", 2),
    DATA("DATA", 3),
    ACKNOWLEDGEMENT("ACK", 4),
    ERROR("ERR", 5);

    /**
     * 数据包类型 RRQ WRQ等
     */
    private final String abbreviation;

    /**
     * 数据包的代码值
     */
    private final short opcode;

    /**
     * 新建包类型
     *
     * @param abbreviation 描述分组类型的短字符串
     * @param opcode 数据包的代码值
     */
    PacketType(String abbreviation, int opcode) {
        this.abbreviation = abbreviation;
        this.opcode = (short) opcode;
    }

    /**
     * @return 数据包的代码值 (任何TFTP包中的前两个字节)
     */
    public short getOpcode() {
        return opcode;
    }

    /**
     * @return  数据包类型 RRQ WRQ等
     */
    @Override
    public String toString() {
        return abbreviation;
    }

    /**
     * 查找与特定操作码相关联的数据包类型。
     *
     * @param opcode the opcode of a packet
     * @return the associated packet type
     * @throws TFTPException if there is not a packet type for the given opcode
     */
    public static PacketType fromOpcode(short opcode) throws TFTPException {
        for (PacketType type : values()) {
            if (type.opcode == opcode) {
                return type;
            }
        }
        throw new TFTPException("no such opcode: " + opcode);
    }

}
