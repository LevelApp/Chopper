package com.levelapp.realmchopper;

import com.levelapp.annotation.Chopperable;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;

/**
 * Created by rafaldziuryk on 05.01.17.
 */

public class RealmChangeListenerChopperable implements Chopperable<RealmObject, RealmChangeListener> {

  @Override
  public void chopp(RealmObject target, RealmChangeListener enclosed) {
    if (target != null && enclosed != null){
      target.removeChangeListener(enclosed);
    }
  }
}
