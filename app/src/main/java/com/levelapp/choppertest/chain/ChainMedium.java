package com.levelapp.choppertest.chain;

import com.levelapp.annotation.ChainChopperable;
import com.levelapp.annotation.Chopp;

/**
 * Created by rafaldziuryk on 10.01.17.
 */

public class ChainMedium {

  @Chopp(chopper = ChainChopperable.class)
  public ChainBottom chainBottom;

  public ChainMedium(ChainBottom chainBottom) {
    this.chainBottom = chainBottom;
  }
}
