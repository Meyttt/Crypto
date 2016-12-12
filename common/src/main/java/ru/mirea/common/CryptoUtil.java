package ru.mirea.common;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * Created by master on 28.11.2016.
 */
public class CryptoUtil {

    public static BigInteger encrypt(String data, BigInteger code) {
        BigInteger intBytes=new BigInteger(data.getBytes(StandardCharsets.UTF_8));
        intBytes=intBytes.xor(code);
        return intBytes;
    }

    public static String decrypt(BigInteger data, BigInteger code) {
        BigInteger intBytes=data.xor(code);
        String res =new String(intBytes.toByteArray(), StandardCharsets.UTF_8);
        return res;
    }
}
