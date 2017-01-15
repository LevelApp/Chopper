package com.levelapp.choppertest.chain;

import com.levelapp.annotation.chopperable.ChainChopperable;
import com.levelapp.annotation.annotations.ChoppOnPause;
import junit.framework.Assert;

/**
 * Created by rafaldziuryk on 10.01.17.
 */

public class ChainField {

  public Object object;

  public ChainBottom chainBottom;

  public ChainMedium chainMedium;

  @ChoppOnPause(ChainChopperable.class)
  public ChainTop chainTop;

  public ChainField(){
    object = new Object();
    chainBottom = new ChainBottom(object);
    chainMedium = new ChainMedium(chainBottom);
    chainTop = new ChainTop(chainMedium);
  }

  public boolean isChopped(){
    Assert.assertTrue(chainBottom.object == null);
    Assert.assertTrue(chainMedium.chainBottom == null);
    return true;
  }

}
