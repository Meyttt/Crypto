package ru.mirea.common;

/**
 * Created by a.chebotareva on 09.12.2016.
 */
public class Exchanger{
    int base;
    int mod;
    public Exchanger(int base, int mod) {
        this.base = base;
        this.mod = mod;
    }

    public int getPublicKey(Integer privateKey){
        return (base^privateKey)%mod;
    }
}