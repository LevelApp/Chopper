package com.levelapp.chopper;

import com.levelapp.annotation.Chopperable;
import rx.Subscription;

public class SubscriptionChopperable implements Chopperable<Subscription> {

  @Override
  public void chopp(Subscription chopp) {
    if (chopp != null && !chopp.isUnsubscribed()){
      chopp.unsubscribe();
    }
  }
}
