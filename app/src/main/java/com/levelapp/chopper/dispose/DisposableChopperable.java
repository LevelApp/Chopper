package com.levelapp.chopper.dispose;

import com.levelapp.annotation.Chopperable;

/**
 * Created by rafaldziuryk on 28.12.16.
 */

public class DisposableChopperable implements Chopperable<Disposable, Object> {

  @Override
  public void chopp(Disposable target, Object enclosed) {
    if (target != null){
      target.dispose();
    }
  }
}
