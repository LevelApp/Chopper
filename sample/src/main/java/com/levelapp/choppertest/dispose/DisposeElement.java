package com.levelapp.choppertest.dispose;

/**
 * Created by rafaldziuryk on 28.12.16.
 */

public class DisposeElement implements Disposable {

  boolean isDisposed = false;

  @Override
  public void dispose() {
    isDisposed = true;
  }

  @Override
  public boolean isDisposed() {
    return isDisposed;
  }
}
