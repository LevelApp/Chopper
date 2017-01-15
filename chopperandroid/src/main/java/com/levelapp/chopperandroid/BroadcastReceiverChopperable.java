package com.levelapp.chopperandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import com.levelapp.annotation.chopperable.Chopperable;

/**
 * Created by rafaldziuryk on 05.01.17.
 */

public class BroadcastReceiverChopperable implements Chopperable<BroadcastReceiver, Context> {

  @Override
  public void chopp(BroadcastReceiver target, Context enclosed) {
    if (enclosed != null && target != null){
      try {
        enclosed.unregisterReceiver(target);
      } catch (IllegalArgumentException error){
        error.printStackTrace();
      }
    }
  }
}
