package com.levelapp.choppertest.dispose;

import com.levelapp.annotation.annotations.ChoppOnPause;


public class DisposeField {

    @ChoppOnPause({DisposableChopperable.class})
    protected DisposeElement disposeElement;

    public DisposeField(DisposeElement disposeElement){
        this.disposeElement = disposeElement;
    }

    public boolean isDisposed(){
        return disposeElement.isDisposed();
    }
}