package com.levelapp.realmchopper;

import com.levelapp.annotation.Chopperable;
import io.realm.Realm;

/**
 * Created by rafaldziuryk on 03.01.17.
 */

public class RealmChopperable implements Chopperable<Realm> {

  @Override
  public void chopp(Realm chopp) {
    if (chopp != null && !chopp.isClosed()){
      chopp.close();
    }
  }
}
