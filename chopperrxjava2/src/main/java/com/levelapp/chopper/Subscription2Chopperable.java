package com.levelapp.chopper;

import com.levelapp.annotation.Chopperable;
import org.reactivestreams.Subscription;

public class Subscription2Chopperable implements Chopperable<Subscription>{

  @Override
  public void chopp(Subscription chopp) {
    if (chopp != null){
      chopp.cancel();
    }
  }
}
