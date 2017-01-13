package com.levelapp.annotation;

import java.util.HashMap;
import java.util.Map;

/**
 * Main class for destroy all objects
 *
 * Created by rafaldziuryk on 28.12.16.
 */

public class Chopper {

  public static final String CHOPPER_PROPERTY = "chopper";
  public static final String SET_NULL_PROPERTY = "setNull";
  public static final String CHOPPER_SUFFIX = "_Chopperable";

  private static final Map<Class, Chopperable> CLASS_MAPPER = new HashMap<>();
  private static final Map<Class<? extends Chopperable>, Chopperable> CHOPPERABLE_MAPPER = new HashMap<Class<? extends Chopperable>, Chopperable>();

  private static BetterProguard betterProguard = new NoBetterProguard();

  public static void init(BetterProguard betterProguard) {
    Chopper.betterProguard = betterProguard;
  }

  /**
   * Used this method to kill all object that you want to kill!
   *
   * @param object object to kill!
   */
  @SuppressWarnings("Used by target application")
  public static void chopp(Object object) {
    Chopperable chopperable = safeChopperClass(object);
    chopp(object, chopperable);
  }

  private static void chopp(Object object, Chopperable chopperable) {
    chopperable.chopp(object, object);
  }

  private static Chopperable safeChopperClass(Object object) {
    if (CLASS_MAPPER.containsKey(object.getClass())) {
      return CLASS_MAPPER.get(object.getClass());
    } else {
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
