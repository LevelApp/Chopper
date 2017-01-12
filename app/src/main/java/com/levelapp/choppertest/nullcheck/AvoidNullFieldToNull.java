package com.levelapp.choppertest.nullcheck;

import com.levelapp.annotation.Chopp;


public class AvoidNullFieldToNull {
    @Chopp(setNull = false)
    public String s1;
    public String s2;

    public AvoidNullFieldToNull(String s1, String s2) {
        this.s1 = s1;
        this.s2 = s2;
    }
}