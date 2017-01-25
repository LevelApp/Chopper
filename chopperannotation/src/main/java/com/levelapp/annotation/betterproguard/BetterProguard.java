package com.levelapp.annotation.betterproguard;

import com.levelapp.annotation.Lifecycler;

/**
 * Created by rafaldziuryk on 12.01.17.
 */

public interface BetterProguard {
  Lifecycler getFactory(Class instance);
}
