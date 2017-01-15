package com.levelapp.annotation.betterproguard;

import com.levelapp.annotation.chopperable.Chopperable;

/**
 * Created by rafaldziuryk on 13.01.17.
 */

public abstract class NoBetterProguard<T extends Chopperable> implements BetterProguard {

  T suffix;

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


  private String prepareClassName(Object object) {
    Class<?> clazz = object.getClass();
    String className = clazz.getCanonicalName();
    className = className + "_" + suffix.getClass().getSimpleName();
    return className;
  }
}
