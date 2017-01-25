package com.levelapp.annotation.betterproguard;

import com.levelapp.annotation.Lifecycler;

/**
 * Created by rafaldziuryk on 13.01.17.
 */

public class NoBetterProguardFactory<T extends Lifecycler> implements BetterProguard {

  Class<T> clazz;

  public NoBetterProguardFactory(Class<T> clazz){
    this.clazz = clazz;
  }

  @Override
  public Lifecycler getFactory(Class clazz) {
    String className = prepareClassName(clazz);
    try {
      Lifecycler lifecycler = (Lifecycler) Class.forName(className).newInstance();
      return lifecycler;
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }


  private String prepareClassName(Class<?> object) {
    String className = object.getCanonicalName();
    className = className + "_" + this.clazz.getSimpleName();
    return className;
  }
}
