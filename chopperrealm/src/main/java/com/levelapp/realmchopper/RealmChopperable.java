package com.levelapp.realmchopper;

import com.levelapp.annotation.Chopperable;
import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by rafaldziuryk on 03.01.17.
 */

public class RealmChopperable implements Chopperable<Realm, Object> {

  @Override
  public void chopp(Realm target, Object enclosed) {
    if (target != null && !target.isClosed()){
      target.close();
    }
  }
}
