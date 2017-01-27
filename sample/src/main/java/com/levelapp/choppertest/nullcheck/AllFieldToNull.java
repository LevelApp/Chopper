package com.levelapp.choppertest.nullcheck;

import com.levelapp.annotation.annotations.Chopp;


public class AllFieldToNull {
    @Chopp
    public String s1;
    @Chopp
    public String s2;

    public AllFieldToNull(String s1, String s2) {
        this.s1 = s1;
        this.s2 = s2;
    }
}