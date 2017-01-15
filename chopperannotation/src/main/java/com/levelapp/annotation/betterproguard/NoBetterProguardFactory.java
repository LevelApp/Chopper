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
  public BetterProguard onPause() {
    return new NoBetterProguard<ChopperableOnPause>() {};
  }

  @Override
  public BetterProguard onStop() {
    return new NoBetterProguard<ChopperableOnStop>() {};
  }

  @Override
  public BetterProguard onDestroyView() {
    return new NoBetterProguard<ChopperableOnDestroyView>() {};
  }

  @Override
  public BetterProguard onDestroy() {
    return new NoBetterProguard<ChopperableOnDestroy>() {};
  }
}
