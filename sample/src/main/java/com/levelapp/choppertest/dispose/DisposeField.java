package com.levelapp.choppertest.dispose;

import com.levelapp.annotation.annotations.ChoppOnPause;


public class DisposeField {

    public static boolean disposed = false;

    @ChoppOnPause({DisposableChopperable.class})
    protected DisposeElement disposeElement;

    public DisposeField(DisposeElement disposeElement){
        this.disposeElement = disposeElement;
    }
}