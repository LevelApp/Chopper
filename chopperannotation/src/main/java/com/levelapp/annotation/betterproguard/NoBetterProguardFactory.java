package com.levelapp.annotation.betterproguard;

import com.levelapp.annotation.chopperable.Chopperable;

/**
 * No proguard implementation. Standard Class.forName() instance creating
 *
 * Created by rafaldziuryk on 13.01.17.
 */

public class NoBetterProguardFactory<T extends Chopperable> implements BetterProguard {

  Class<T> clazz;

  public NoBetterProguardFactory(Class<T> clazz){
    this.clazz = clazz;
  }

  @Override
  public Chopperable getFactory(Class clazz) {
    while(!clazz.equals(Object.class)) {
      try {
        String className = prepareClassName(clazz);
        Chopperable lifecycler = (Chopperable) Class.forName(className).newInstance();
        return lifecycler;
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
      clazz = clazz.getSuperclass();
    }
    return null;
  }


  private String prepareClassName(Class<?> object) {
    String className = object.getCanonicalName();
    className = className + "_" + this.clazz.getSimpleName();
    return className;
  }
}
