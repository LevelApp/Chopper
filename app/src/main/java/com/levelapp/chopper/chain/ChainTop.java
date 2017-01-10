package com.levelapp.chopper.chain;

import com.levelapp.annotation.ChainChopperable;
import com.levelapp.annotation.Chopp;

/**
 * Created by rafaldziuryk on 10.01.17.
 */

public class ChainTop {

  @Chopp(chopper = ChainChopperable.class)
  public ChainMedium chainMedium;

  public ChainTop(ChainMedium chainMedium){
    this.chainMedium = chainMedium;
  }


}
