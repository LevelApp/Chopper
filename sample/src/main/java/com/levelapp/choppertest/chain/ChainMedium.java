package com.levelapp.choppertest.chain;

import com.levelapp.annotation.chopperable.ChainChopperable;
import com.levelapp.annotation.annotations.ChoppOnPause;

/**
 * Created by rafaldziuryk on 10.01.17.
 */

public class ChainMedium {

  @ChoppOnPause(ChainChopperable.class)
  public ChainBottom chainBottom;

  public ChainMedium(ChainBottom chainBottom) {
    this.chainBottom = chainBottom;
  }
}
