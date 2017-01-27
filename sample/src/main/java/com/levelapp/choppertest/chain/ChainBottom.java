package com.levelapp.choppertest.chain;

import com.levelapp.annotation.annotations.Chopp;

/**
 * Created by rafaldziuryk on 10.01.17.
 */

public class ChainBottom {
  @Chopp
  public Object object;

  public ChainBottom(Object object){
    this.object = object;
  }
}
