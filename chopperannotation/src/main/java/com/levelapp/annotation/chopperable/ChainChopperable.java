package com.levelapp.annotation.chopperable;

import com.levelapp.annotation.Chopper;

/**
 * Created by rafaldziuryk on 09.01.17.
 */

public class ChainChopperable implements Chopperable<Object, Object> {

  @Override
  public void chopp(Object target, Object enclosed, int level) {
      Chopper.chopp(target, level);
  }
}
