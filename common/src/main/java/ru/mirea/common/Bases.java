package ru.mirea.common;

/**
 * Created by a.chebotareva on 09.12.2016.
 */
public class Bases {
    String base;
    String module;
    public Bases(String base, String module) {
        this.base = base;
        this.module = module;
    }
    public Bases(int base, int module){
        this.base = String.valueOf(base);
        this.base = String.valueOf(module);
    }


}
