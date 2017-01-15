package com.levelapp.choppertest.chain;

import com.levelapp.annotation.chopperable.ChainChopperable;
import com.levelapp.annotation.annotations.ChoppOnPause;

/**
 * Created by rafaldziuryk on 10.01.17.
 */

public class ChainTop {

  @ChoppOnPause(ChainChopperable.class)
  public ChainMedium chainMedium;

  public ChainTop(ChainMedium chainMedium){
    this.chainMedium = chainMedium;
  }
}
