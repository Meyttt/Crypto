package ru.mirea.common;

import java.math.BigInteger;

/**
 * Created by master on 28.11.2016.
 */
public class Base {

    private BigInteger generator;
    private BigInteger module;

    public BigInteger getModule() {
        return module;
    }

    public BigInteger getGenerator() {
        return generator;
    }

    public Base(BigInteger generator, BigInteger module) {

        this.generator = generator;
        this.module = module;
    }
    public BigInteger getPublicKey(Integer privateKey){
        return generator.pow(privateKey).mod(module);
    }
    public BigInteger getCode(Integer privateKey, BigInteger otherPublicKey){
        return otherPublicKey.pow(privateKey).mod(module);
    }
}
