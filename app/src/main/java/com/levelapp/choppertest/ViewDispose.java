package com.levelapp.choppertest;

import static android.R.attr.tag;

import android.view.View;
import com.levelapp.annotation.Chopperable;

/**
 * Created by rafaldziuryk on 10.01.17.
 */

public class ViewDispose implements Chopperable<View, Object> {

  @Override
  public void chopp(View target, Object enclosed) {
    if (target != null){
      target.setOnClickListener(null);
    }
  }
}
