package com.levelapp.annotation.betterproguard;

import com.levelapp.annotation.chopperable.Chopperable;

/**
 * Created by rafaldziuryk on 12.01.17.
 */

public interface BetterProguard {
  public Chopperable getFactory(Object instance);
}
