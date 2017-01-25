package com.levelapp.annotation;

import android.os.Bundle;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rafaldziuryk on 18.01.17.
 */

public class ParameterHelper {
  public final static Map<Class<?>, String> BUNDLE_MAP = new HashMap<>(1);

  public final static Map<Class<?>, String> EMPTY_MAP = new HashMap<>(0);

  static {
    BUNDLE_MAP.put(Bundle.class, "bundle");
  }
}
