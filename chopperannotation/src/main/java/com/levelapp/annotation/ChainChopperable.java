package com.levelapp.annotation;

/**
 * Created by rafaldziuryk on 09.01.17.
 */

public class ChainChopperable implements Chopperable<Object, Object> {

  @Override
  public void chopp(Object target, Object enclosed) {
    Chopper.chopp(target);
  }
}
