package com.levelapp.annotation;

import com.levelapp.annotation.betterproguard.BetterProguard;
import com.levelapp.annotation.betterproguard.NoBetterProguardFactory;
import com.levelapp.annotation.chopperable.Chopperable;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

/**
 * Main class for onDestroy all objects
 *
 * Created by rafaldziuryk on 28.12.16.
 */

public class Chopper {

  public static final String CHOPPER_PROPERTY = "value";
  public static final String LEVEL_PROPERTY = "level";

  private static final Map<Class<? extends Chopperable>, Chopperable> CHOPPERABLE_MAPPER = new HashMap<>();
  private static final Map<Class<?>, Chopperable> LIFECYCLER_MAP = new HashMap<>();

  private static BetterProguard betterProguardFactory = new NoBetterProguardFactory(Chopperable.class);

  /**
   * Provide factory method to get minified Chopperable class
   *
   * @param betterProguardFactory instance creating implementation for full proguard support
   */
  public static void init(@Nullable BetterProguard betterProguardFactory) {
    if (betterProguardFactory == null){
      betterProguardFactory = new NoBetterProguardFactory<>(Chopperable.class);
    }
    Chopper.betterProguardFactory = betterProguardFactory;
  }

  /**
   * Used this method to kill all object that you want to kill!
   *
   * @param object object to kill!
   */

  @SuppressWarnings("Used by target application")
  public static void chopp (Object object) {
    if (object != null) {
      Chopperable chopperabler = safeChopperObject(object);
      chopperabler.chopp(object, object, Chopperable.DEFAULT_LEVEL);
    }
  }

  @SuppressWarnings("Used by target application")
  public static void chopp (Object object, int level) {
    if (object != null) {
      Chopperable chopperabler = safeChopperObject(object);
      chopperabler.chopp(object, object, level);
    }
  }

  public static Chopperable safeChopperObject(Object object) {
    return safeChopperClass(object.getClass());
  }

  public static Chopperable safeChopperClass(Class<?> object) {
    if (LIFECYCLER_MAP.containsKey(object)) {
      return LIFECYCLER_MAP.get(object);
    } else {
      Chopperable chopperable = betterProguardFactory.getFactory(object);
      LIFECYCLER_MAP.put(object, chopperable);
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
