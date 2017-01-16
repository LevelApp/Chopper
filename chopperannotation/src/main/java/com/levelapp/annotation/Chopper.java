package com.levelapp.annotation;

import com.levelapp.annotation.betterproguard.BetterProguard;
import com.levelapp.annotation.betterproguard.BetterProguardFactory;
import com.levelapp.annotation.betterproguard.NoBetterProguardFactory;
import com.levelapp.annotation.chopperable.Chopperable;
import java.util.HashMap;
import java.util.Map;

/**
 * Main class for onDestroy all objects
 *
 * Created by rafaldziuryk on 28.12.16.
 */

public class Chopper {

  public static final String CHOPPER_PROPERTY = "value";

  private static final Map<Class, Chopperable> CLASS_MAPPER = new HashMap<>();
  private static final Map<Class<? extends Chopperable>, Chopperable> CHOPPERABLE_MAPPER = new HashMap<Class<? extends Chopperable>, Chopperable>();

  private static BetterProguardFactory betterProguardFactory = new NoBetterProguardFactory();

  /**
   * Provide factory method to get minified Chopperable class
   */
  public static void init(BetterProguardFactory betterProguardFactory) {
    Chopper.betterProguardFactory = betterProguardFactory;
  }

  /**
   * Used this method to kill all object that you want to kill!
   *
   * @param object object to kill!
   */
  @SuppressWarnings("Used by target application")
  public static void onPause(Object object) {
    Chopperable chopperable = safeChopperClass(object, Lifecycle.PAUSE);
    chopp(object, chopperable, Lifecycle.PAUSE);
  }

  public static void onStop(Object object) {
    Chopperable chopperable = safeChopperClass(object, Lifecycle.STOP);
    chopp(object, chopperable, Lifecycle.STOP);
  }

  public static void onDestroyView(Object object) {
    Chopperable chopperable = safeChopperClass(object, Lifecycle.DESTROY_VIEW);
    chopp(object, chopperable, Lifecycle.DESTROY_VIEW);
  }

  public static void onDestroy(Object object) {
    Chopperable chopperable = safeChopperClass(object, Lifecycle.DESTROY);
    chopp(object, chopperable, Lifecycle.DESTROY);
  }

  private static void chopp(Object object, Chopperable chopperable, Lifecycle lifecycle) {
    chopperable.chopp(object, object, lifecycle);
  }

  private static Chopperable safeChopperClass(Object object, Lifecycle lifecycle) {
    if (CLASS_MAPPER.containsKey(object.getClass())) {
      return CLASS_MAPPER.get(object.getClass());
    } else {
      BetterProguard betterProguard = null;
      switch (lifecycle){
        case PAUSE:
          betterProguard = betterProguardFactory.chopperableOnPause();
          break;
        case STOP:
          betterProguard = betterProguardFactory.chopperableOnStop();
          break;
        case DESTROY_VIEW:
          betterProguard = betterProguardFactory.chopperableOnDestroyView();
          break;
        case DESTROY:
          betterProguard = betterProguardFactory.chopperableOnDestroy();
          break;
      }
      Chopperable chopperable = betterProguard.getFactory(object);
      CLASS_MAPPER.put(object.getClass(), chopperable);
      return chopperable;
    }
  }

  @SuppressWarnings("Used by ChopperProcessor")
  public static Chopperable safeChopperableClass(Class<? extends Chopperable> clazz) {
    if (CHOPPERABLE_MAPPER.containsKey(clazz)) {
      return CHOPPERABLE_MAPPER.get(clazz);
    } else {
      Chopperable chopperable = null;
      try {
        chopperable = clazz.newInstance();
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
      CHOPPERABLE_MAPPER.put(clazz, chopperable);
      return chopperable;
    }
  }
}
