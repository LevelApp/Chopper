package com.levelapp.chopper.nullcheck;

import com.levelapp.annotation.Chopp;


public class SomeFieldToNull {
    @Chopp
    public String s1;
    public String s2;

    public SomeFieldToNull(String s1, String s2) {
        this.s1 = s1;
        this.s2 = s2;
    }
}