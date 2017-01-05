package com.levelapp.chopper;

import com.levelapp.annotation.Chopperable;
import rx.Subscription;

public class SubscriptionChopperable implements Chopperable<Subscription, Object> {

  @Override
  public void chopp(Subscription target, Object enclosed) {
    if (target != null && !target.isUnsubscribed()){
      target.unsubscribe();
    }
  }
}
