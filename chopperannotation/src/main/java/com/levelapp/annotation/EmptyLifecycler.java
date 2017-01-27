package com.levelapp.annotation;

import com.levelapp.annotation.chopperable.Chopperable;

/**
 * Created by rafaldziuryk on 25.01.17.
 */

public class EmptyLifecycler implements Chopperable {

  @Override
  public void chopp(Object instance, Object enclosed, int level) {

  }
}
