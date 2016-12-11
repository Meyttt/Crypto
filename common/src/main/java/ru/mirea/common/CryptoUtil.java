package ru.mirea.common;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by master on 28.11.2016.
 */
public class CryptoUtil {

    public static String encrypt(String data, BigInteger code) {
        System.out.println("Encrypting " + data + " with " + code);
        BigInteger intBytes=new BigInteger(data.getBytes(StandardCharsets.UTF_8));

        intBytes=intBytes.add(code);
//        intBytes=intBytes.xor(code);
        String res =new String(intBytes.toByteArray(), StandardCharsets.UTF_8);
        return res;
    }

    public static String decrypt(String data, BigInteger code) {
        System.out.println("Decrypting " + data + " with " + code);
        BigInteger intBytes=new BigInteger(data.getBytes(StandardCharsets.UTF_8));
        intBytes=intBytes.subtract(code);
        String res =new String(intBytes.toByteArray(), StandardCharsets.UTF_8);
        return res;
    }
}
