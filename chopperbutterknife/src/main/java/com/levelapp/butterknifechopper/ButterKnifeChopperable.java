package com.levelapp.butterknifechopper;

import butterknife.Unbinder;
import com.levelapp.annotation.Chopperable;

/**
 * Created by rafaldziuryk on 03.01.17.
 */

public class ButterKnifeChopperable implements Chopperable<Unbinder, Object> {

  @Override
  public void chopp(Unbinder target, Object enclosed) {
    if (target != null){
      target.unbind();
    }
  }
}
