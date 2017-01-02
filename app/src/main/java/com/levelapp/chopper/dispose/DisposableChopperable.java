package com.levelapp.chopper.dispose;

import com.levelapp.annotation.Chopperable;

/**
 * Created by rafaldziuryk on 28.12.16.
 */

public class DisposableChopperable implements Chopperable<Disposable> {

  @Override
  public void chopp(Disposable chopp){
    chopp.dispose();
  }
}
