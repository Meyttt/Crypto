package ru.mirea;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by Admin on 20.09.2016.
 */
public class dbCell implements Serializable{
    String hash;
    String salt;
    dbCell(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Long date = new Date().getTime();
        this.salt=date.toString();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String text = password+salt;
        md.update(text.getBytes("UTF-8"));
        byte[] digest = md.digest();
        this.hash=String.format("%064x",new BigInteger(1,digest));

    }
    public boolean correctPassword(String testPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update((testPassword+this.salt).getBytes("UTF-8"));
        byte[] digest = md.digest();
        return this.hash.equals(String.format("%064x",new BigInteger(1,digest)));
    }
    @Override
    public String toString(){
        return " salt: "+salt+" hash: "+hash;
    }

}
