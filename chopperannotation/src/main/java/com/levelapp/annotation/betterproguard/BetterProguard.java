package com.levelapp.annotation.betterproguard;

import com.levelapp.annotation.chopperable.Chopperable;

/**
 * Better proguard interface.
 *
 * Created by rafaldziuryk on 12.01.17.
 */

public interface BetterProguard {
  Chopperable getFactory(Class instance);
}
