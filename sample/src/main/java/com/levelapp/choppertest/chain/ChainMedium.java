package com.levelapp.choppertest.chain;

import com.levelapp.annotation.annotations.Chopp;
import com.levelapp.annotation.chopperable.ChainChopperable;

/**
 * Created by rafaldziuryk on 10.01.17.
 */

public class ChainMedium {

  @Chopp(ChainChopperable.class)
  public ChainBottom chainBottom;

  public ChainMedium(ChainBottom chainBottom) {
    this.chainBottom = chainBottom;
  }
}
