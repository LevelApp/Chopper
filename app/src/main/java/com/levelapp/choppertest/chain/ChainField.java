package com.levelapp.choppertest.chain;

import com.levelapp.annotation.ChainChopperable;
import com.levelapp.annotation.Chopp;
import junit.framework.Assert;

/**
 * Created by rafaldziuryk on 10.01.17.
 */

public class ChainField {

  public Object object;

  public ChainBottom chainBottom;

  public ChainMedium chainMedium;

  @Chopp(chopper = ChainChopperable.class)
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
