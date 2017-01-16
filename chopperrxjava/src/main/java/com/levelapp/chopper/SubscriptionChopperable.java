package com.levelapp.chopper;

import com.levelapp.annotation.Lifecycle;
import com.levelapp.annotation.chopperable.Chopperable;
import rx.Subscription;

public class SubscriptionChopperable implements Chopperable<Subscription, Object> {

  @Override
  public void chopp(Subscription target, Object enclosed, Lifecycle lifecycle) {
    if (target != null && !target.isUnsubscribed()){
      target.unsubscribe();
    }
  }
}
