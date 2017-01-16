package com.levelapp.annotation.betterproguard;

/**
 * Created by rafaldziuryk on 15.01.17.
 */

public interface BetterProguardFactory {
  BetterProguard chopperableOnPause();
  BetterProguard chopperableOnStop();
  BetterProguard chopperableOnDestroyView();
  BetterProguard chopperableOnDestroy();
}
