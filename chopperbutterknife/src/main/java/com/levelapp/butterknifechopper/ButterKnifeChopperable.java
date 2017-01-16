package com.levelapp.butterknifechopper;

import butterknife.Unbinder;
import com.levelapp.annotation.Lifecycle;
import com.levelapp.annotation.chopperable.Chopperable;

/**
 * Created by rafaldziuryk on 03.01.17.
 */

public class ButterKnifeChopperable implements Chopperable<Unbinder, Object> {

  @Override
  public void chopp(Unbinder target, Object enclosed, Lifecycle lifecycle) {
    if (target != null){
      target.unbind();
    }
  }
}
