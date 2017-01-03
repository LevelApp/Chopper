package com.levelapp.butterknifechopper;

import butterknife.Unbinder;
import com.levelapp.annotation.Chopperable;

/**
 * Created by rafaldziuryk on 03.01.17.
 */

public class ButterKnifeChopperable implements Chopperable<Unbinder> {

  @Override
  public void chopp(Unbinder chopp) {
    chopp.unbind();
  }
}
