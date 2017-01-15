package com.levelapp.annotation.chopperable;

import com.levelapp.annotation.Chopper;
import com.levelapp.annotation.chopperable.Chopperable;

/**
 * Created by rafaldziuryk on 09.01.17.
 */

public class ChainChopperable implements Chopperable<Object, Object> {

  @Override
  public void chopp(Object target, Object enclosed) {
    Chopper.onDestroy(target);
  }
}
