package com.levelapp.realmchopper;

import com.levelapp.annotation.chopperable.Chopperable;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Created by rafaldziuryk on 05.01.17.
 */

public class RealmResultChangeListenerChopperable implements Chopperable<RealmResults<? extends RealmModel>, RealmChangeListener> {

  @Override
  public void chopp(RealmResults<? extends RealmModel> target, RealmChangeListener enclosed) {
    if (target != null && enclosed != null){
      target.removeChangeListener(enclosed);
    }
  }
}
