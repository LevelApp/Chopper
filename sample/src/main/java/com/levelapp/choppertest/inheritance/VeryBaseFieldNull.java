package com.levelapp.choppertest.inheritance;

import com.levelapp.annotation.annotations.ChoppOnPause;
import com.levelapp.choppertest.dispose.DisposableChopperable;
import com.levelapp.choppertest.dispose.DisposeElement;

/**
 * Created by rafaldziuryk on 01.01.17.
 */

public abstract class VeryBaseFieldNull {

  @ChoppOnPause({DisposableChopperable.class})
  public DisposeElement s1;
}
