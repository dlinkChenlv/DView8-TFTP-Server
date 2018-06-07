package dlink.tftp.util;

/**
 * Created by chenlv on 2018.5.28.
 *
 * TFTP 传输模式
 */
public enum Mode {

    ASCII("netascii"),
    OCTET("octet"),
    MAIL("mail");

    private final String name;
    /**
     *
     * @param name
     */
    Mode(String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @return same as {@link #getName()}
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     *
     * @param name
     * @return
     * @throws TFTPException
     */
    public static Mode fromName(String name) throws TFTPException {
        for (Mode mode : values()) {
            if (mode.name.equalsIgnoreCase(name)) {
                return mode;
            }
        }
        throw new TFTPException("no such mode: " + name);
    }

}
