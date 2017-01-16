package com.levelapp.chopper;

import com.levelapp.annotation.Lifecycle;
import com.levelapp.annotation.chopperable.Chopperable;
import org.reactivestreams.Subscription;

public class Subscription2Chopperable implements Chopperable<Subscription, Object>{

  @Override
  public void chopp(Subscription target, Object enclosed, Lifecycle lifecycle) {
    if (target != null){
      target.cancel();
    }
  }
}
