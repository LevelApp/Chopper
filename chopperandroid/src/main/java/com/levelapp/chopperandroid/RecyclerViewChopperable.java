package com.levelapp.chopperandroid;

import android.support.v7.widget.RecyclerView;
import com.levelapp.annotation.chopperable.Chopperable;

/**
 * Created by rafaldziuryk on 05.01.17.
 */

public class RecyclerViewChopperable implements Chopperable<RecyclerView, Object> {

  @Override
  public void chopp(RecyclerView target, Object enclosed, int level) {
    if (target != null){
      target.setAdapter(null);
    }
  }
}
