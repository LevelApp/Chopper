package com.levelapp.choppertest.dispose;

import com.levelapp.annotation.Chopp;


public class DisposeField {

    @Chopp(chopper = {DisposableChopperable.class}, setNull = false)
    protected DisposeElement disposeElement;

    public DisposeField(DisposeElement disposeElement){
        this.disposeElement = disposeElement;
    }

    public boolean isDisposed(){
        return disposeElement.isDisposed();
    }
}