package com.levelapp.chopper.inheritance;

import com.levelapp.annotation.Chopp;
import com.levelapp.chopper.dispose.DisposableChopperable;
import com.levelapp.chopper.dispose.DisposeElement;

/**
 * Created by rafaldziuryk on 01.01.17.
 */

public abstract class VeryBaseFieldNull {

  @Chopp(chopper = {DisposableChopperable.class})
  public DisposeElement s1;
}
