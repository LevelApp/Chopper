package com.levelapp.chopper;

import com.levelapp.annotation.chopperable.Chopperable;
import org.reactivestreams.Subscription;

public class Subscription2Chopperable implements Chopperable<Subscription, Object>{

  @Override
  public void chopp(Subscription target, Object enclosed) {
    if (target != null){
      target.cancel();
    }
  }
}
