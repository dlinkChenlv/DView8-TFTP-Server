package dlink.tftp.util;

import java.nio.charset.StandardCharsets;

/**
 * Created by chenlv on 2018.5.28.
 * 解析TFTP分组的字符串
 */
public class StringUtil {

    /**
     *把字符串转成字节
     *
     * @param string
     * @return
     */
    public static byte[] getBytes(String string) {
        //get bytes with correct character format
        byte[] bytes = string.getBytes(StandardCharsets.US_ASCII);
        byte[] addNull = new byte[bytes.length + 1];
        System.arraycopy(bytes, 0, addNull, 0, bytes.length);
        //add a null character at the end
        addNull[addNull.length - 1] = 0;
        return addNull;
    }

    /**
     * 把字节转换成字符串
     *
     * @param bytes
     * @param offset
     * @return
     */
    public static String getString(byte[] bytes, int offset) {
        int nullPos = offset;
        while (nullPos < bytes.length && bytes[nullPos] != 0) {
            ++nullPos;
        }
        int length = nullPos - offset;
        return new String(bytes, offset, length, StandardCharsets.US_ASCII);
    }

}
