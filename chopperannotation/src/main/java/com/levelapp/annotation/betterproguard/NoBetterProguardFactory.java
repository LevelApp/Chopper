package com.levelapp.annotation.betterproguard;

import com.levelapp.annotation.chopperable.Chopperable;
import com.levelapp.annotation.chopperable.ChopperableOnDestroy;
import com.levelapp.annotation.chopperable.ChopperableOnDestroyView;
import com.levelapp.annotation.chopperable.ChopperableOnPause;
import com.levelapp.annotation.chopperable.ChopperableOnStop;

/**
 * Created by rafaldziuryk on 15.01.17.
 */

public class NoBetterProguardFactory implements BetterProguardFactory {

  @Override
  public BetterProguard chopperableOnPause() {
    return new NoBetterProguard<ChopperableOnPause>(ChopperableOnPause.class) {};
  }

  @Override
  public BetterProguard chopperableOnStop() {
    return new NoBetterProguard<ChopperableOnStop>(ChopperableOnStop.class) {};
  }

  @Override
  public BetterProguard chopperableOnDestroyView() {
    return new NoBetterProguard<ChopperableOnDestroyView>(ChopperableOnDestroyView.class) {};
  }

  @Override
  public BetterProguard chopperableOnDestroy() {
    return new NoBetterProguard<ChopperableOnDestroy>(ChopperableOnDestroy.class) {};
  }
}
