package ru.mirea.common;

import java.math.BigInteger;

/**
 * Created by master on 28.11.2016.
 */
public class KeyMessage {

    private BigInteger key;

    public KeyMessage(BigInteger key) {
        this.key = key;
    }

    public BigInteger getKey() {
        return key;
    }
}
