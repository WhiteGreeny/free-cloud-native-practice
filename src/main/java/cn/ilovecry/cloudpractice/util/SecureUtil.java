package cn.ilovecry.cloudpractice.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SecureUtil
 *
 * @author yangyi
 * @version 1.0
 * @date 2023/12/1 17:43
 */
public class SecureUtil {

    private final static char[] HEX_CHAR_ARR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String sha1Hex(String str) {
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            sha1.update(str.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = sha1.digest();
            return bytes2Hex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String bytes2Hex(byte[] bytes) {
        char[] arr = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            arr[i * 2] = HEX_CHAR_ARR[bytes[i] >>> 4 & 0xf];
            arr[i * 2 + 1] = HEX_CHAR_ARR[bytes[i] & 0xf];
        }
        return new String(arr);
    }
}
