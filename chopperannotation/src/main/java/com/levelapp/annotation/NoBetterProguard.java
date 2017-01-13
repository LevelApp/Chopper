package com.levelapp.annotation;

import static com.levelapp.annotation.Chopper.CHOPPER_SUFFIX;

/**
 * Created by rafaldziuryk on 13.01.17.
 */

public class NoBetterProguard implements BetterProguard {

  @Override
  public Chopperable getFactory(Object instance) {
    String className = prepareClassName(instance);
    try {
      Chopperable chopperable = (Chopperable) Class.forName(className).newInstance();
      return chopperable;
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }


  private static String prepareClassName(Object object) {
    Class<?> clazz = object.getClass();
    String className = clazz.getCanonicalName();
    className = className + CHOPPER_SUFFIX;
    return className;
  }
}
