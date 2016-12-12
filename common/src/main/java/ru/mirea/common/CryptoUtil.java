package ru.mirea.common;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * Created by master on 28.11.2016.
 */
public class CryptoUtil {

    public static String encrypt(String data, BigInteger code) {
//        System.out.println("Encrypting " + data + " with " + code);
        BigInteger intBytes=new BigInteger(data.getBytes(StandardCharsets.UTF_8));
        System.out.println(intBytes);
        intBytes=intBytes.xor(code);
        System.out.println(intBytes);
        System.out.println();
        String res =new String(intBytes.toByteArray(), StandardCharsets.UTF_8);
        return res;
    }

    public static String decrypt(String data, BigInteger code) {
//        System.out.println("Decrypting " + data + " with " + code);
        BigInteger intBytes=new BigInteger(data.getBytes(StandardCharsets.UTF_8));
        System.out.println(intBytes);
        intBytes=intBytes.xor(code);
        System.out.println(intBytes);
        String res =new String(intBytes.toByteArray(), StandardCharsets.UTF_8);
        return res;
    }
}
