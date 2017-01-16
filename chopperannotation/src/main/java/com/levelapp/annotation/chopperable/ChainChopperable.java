package com.levelapp.annotation.chopperable;

import static com.levelapp.annotation.Lifecycle.DESTROY;

import com.levelapp.annotation.Chopper;
import com.levelapp.annotation.Lifecycle;
import com.levelapp.annotation.chopperable.Chopperable;

/**
 * Created by rafaldziuryk on 09.01.17.
 */

public class ChainChopperable implements Chopperable<Object, Object> {

  @Override
  public void chopp(Object target, Object enclosed, Lifecycle lifecycle) {
    switch (lifecycle){
      case PAUSE:
        Chopper.onPause(target);
        break;
      case STOP:
        Chopper.onPause(target);
        break;
      case DESTROY_VIEW:
        Chopper.onPause(target);
        break;
      case DESTROY:
        Chopper.onPause(target);
        break;
    }
  }
}
