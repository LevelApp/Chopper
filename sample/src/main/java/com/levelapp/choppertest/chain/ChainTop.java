package com.levelapp.choppertest.chain;

import com.levelapp.annotation.annotations.Chopp;
import com.levelapp.annotation.chopperable.ChainChopperable;

/**
 * Created by rafaldziuryk on 10.01.17.
 */

public class ChainTop {

  @Chopp(ChainChopperable.class)
  public ChainMedium chainMedium;

  public ChainTop(ChainMedium chainMedium){
    this.chainMedium = chainMedium;
  }
}
