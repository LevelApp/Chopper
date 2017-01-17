package com.levelapp.annotation;

import com.levelapp.annotation.chopperable.Chopperable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rafaldziuryk on 15.01.17.
 */

public enum Lifecycle {
  PAUSE,
  STOP,
  DESTROY_VIEW,
  DESTROY;

  private final Map<Class, Chopperable> mapper = new HashMap<>();

  public boolean contains(Class key){
    return mapper.containsKey(key);
  }

  public Chopperable get(Class key){
    return mapper.get(key);
  }

  public void put(Class clazz, Chopperable chopperable){
    mapper.put(clazz, chopperable);
  }

}
