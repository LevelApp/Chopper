package com.levelapp.choppertest.chain;

import com.levelapp.annotation.annotations.ChoppOnPause;

/**
 * Created by rafaldziuryk on 10.01.17.
 */

public class ChainBottom {
  @ChoppOnPause
  public Object object;

  public ChainBottom(Object object){
    this.object = object;
  }
}
