package dlink.tftp.util;

/**
 * Created by chenlv on 2018.5.28.
 *
 * TFTP RFC中的错误
 */
public enum ErrorType {

    UNDEFINED(0, "Not defined."),
    FILE_NOT_FOUND(1, "File not found."),
    ACCESS_VIOLATION(2, "Access violation."),
    DISK_FULL(3, "Disk full or allocation exceeded."),
    ILLEGAL_OPERATION(4, "Illegal TFTP operation."),
    UNKNOWN_ID(5, "Unknown transfer ID."),
    FILE_EXISTS(6, "File already exists."),
    NO_SUCH_USER(7, "No such user.");

    private final short value;
    private final String meaning;

    /**
     * @param value
     * @param meaning
     */
    ErrorType(int value, String meaning) {
        this.value = (short) value;
        this.meaning = meaning;
    }

    /**
     * @return
     */
    public short getValue() {
        return value;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return meaning;
    }

    /**
     * 通过传递错误值返回错误代码
     * @param value
     * @return
     */
    public static ErrorType fromValue(int value) {
        for (ErrorType error : values()) {
            if (error.value == value) {
                return error;
            }
        }
        return UNDEFINED;
    }

}
