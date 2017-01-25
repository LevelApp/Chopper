package com.levelapp.annotation;

//import com.levelapp.annotation.betterproguard.BetterProguard;
import android.os.Bundle;
import com.levelapp.annotation.betterproguard.BetterProguard;
//import com.levelapp.annotation.betterproguard.NoBetterProguardFactory;
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

  private static final Map<Class<? extends Chopperable>, Chopperable> CHOPPERABLE_MAPPER = new HashMap<>();
  private static final Map<Class<?>, Lifecycler> LIFECYCLER_MAP = new HashMap<>();

  private static BetterProguard betterProguardFactory = new NoBetterProguardFactory(Chopperable.class);

  /**
   * Provide factory method to get minified Chopperable class
   */
  public static void init(BetterProguard betterProguardFactory) {
    Chopper.betterProguardFactory = betterProguardFactory;
  }

  /**
   * Used this method to kill all object that you want to kill!
   *
   * @param object object to kill!
   */
  @SuppressWarnings("Used by target application")
  public static void onCreate(Object object, Bundle bundle) {
    Lifecycler lifecycler = safeChopperObject(object);
    lifecycler.onCreate(object, object, bundle);
    lifecycler.chopp(object, object);
  }

  @SuppressWarnings("Used by target application")
  public static void onStart(Object object) {
    Lifecycler lifecycler = safeChopperObject(object);
    lifecycler.onStart(object, object);
    lifecycler.chopp(object, object);
  }

  @SuppressWarnings("Used by target application")
  public static void onResume(Object object) {
    Lifecycler lifecycler = safeChopperObject(object);
    lifecycler.onResume(object, object);
    lifecycler.chopp(object, object);
  }

  @SuppressWarnings("Used by target application")
  public static void onPause  (Object object) {
    Lifecycler lifecycler = safeChopperObject(object);
    lifecycler.onPause(object, object);
    lifecycler.chopp(object, object);
  }

  @SuppressWarnings("Used by target application")
  public static void onStop  (Object object) {
    Lifecycler lifecycler = safeChopperObject(object);
    lifecycler.onStop(object, object);
    lifecycler.chopp(object, object);
  }

  @SuppressWarnings("Used by target application")
  public static void onDestroy  (Object object) {
    Lifecycler lifecycler = safeChopperObject(object);
    lifecycler.onDestroy(object, object);
    lifecycler.chopp(object, object);
  }

  @SuppressWarnings("Used by target application")
  public static void onSaveState  (Object object, Bundle bundle) {
    Lifecycler lifecycler = safeChopperObject(object);
    lifecycler.onSaveInstanceState(object, object, bundle);
    lifecycler.chopp(object, object);
  }

  @SuppressWarnings("Used by target application")
  public static void onRestoreState (Object object, Bundle bundle) {
    Lifecycler lifecycler = safeChopperObject(object);
    lifecycler.onRestoreInstanceState(object, object, bundle);
    lifecycler.chopp(object, object);
  }

  @SuppressWarnings("Used by target application")
  public static void onDestroyView (Object object, Bundle bundle) {
    Lifecycler lifecycler = safeChopperObject(object);
    lifecycler.onDestroyView(object, object);
    lifecycler.chopp(object, object);
  }

  @SuppressWarnings("Used by target application")
  public static void onCreateView (Object object, Bundle bundle) {
    Lifecycler lifecycler = safeChopperObject(object);
    lifecycler.onCreateView(object, object, bundle);
    lifecycler.chopp(object, object);
  }

  @SuppressWarnings("Used by target application")
  public static void onDestroyView (Object object) {
    Lifecycler lifecycler = safeChopperObject(object);
    lifecycler.onDestroyView(object, object);
    lifecycler.chopp(object, object);
  }

  @SuppressWarnings("Used by target application")
  public static void chopp (Object object) {
    Lifecycler lifecycler = safeChopperObject(object);
    lifecycler.chopp(object, object);
  }

  public static Lifecycler safeChopperObject(Object object) {
    return safeChopperClass(object.getClass());
  }

  public static Lifecycler safeChopperClass(Class<?> object) {
    if (LIFECYCLER_MAP.containsKey(object)) {
      return LIFECYCLER_MAP.get(object);
    } else {
      Lifecycler lifecycle = betterProguardFactory.getFactory(object);
      LIFECYCLER_MAP.put(object, lifecycle);
      return lifecycle;
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
