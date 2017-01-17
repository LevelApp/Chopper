package com.levelapp.choppertest.dispose;

/**
 * Created by rafaldziuryk on 28.12.16.
 */

public class DisposeElement implements Disposable {


  @Override
  public void dispose() {
    DisposeField.disposed = true;
  }
}
