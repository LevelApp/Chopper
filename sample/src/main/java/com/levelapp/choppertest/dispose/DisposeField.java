package com.levelapp.choppertest.dispose;

import com.levelapp.annotation.annotations.Chopp;


public class DisposeField {

    public static boolean disposed = false;

    @Chopp({DisposableChopperable.class})
    protected DisposeElement disposeElement;

    public DisposeField(DisposeElement disposeElement){
        this.disposeElement = disposeElement;
    }
}