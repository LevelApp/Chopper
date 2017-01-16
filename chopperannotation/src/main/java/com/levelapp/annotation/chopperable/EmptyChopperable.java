package com.levelapp.annotation.chopperable;

import com.levelapp.annotation.Lifecycle;

/**
 * Created by rafaldziuryk on 15.01.17.
 */

public class EmptyChopperable implements Chopperable {

  @Override
  public void chopp(Object target, Object enclosed, Lifecycle lifecycle) {

  }
}
