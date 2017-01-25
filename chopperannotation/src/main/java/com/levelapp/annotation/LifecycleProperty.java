package com.levelapp.annotation;

import com.levelapp.annotation.annotations.Chopp;
import com.levelapp.annotation.annotations.ChoppOnCreate;
import com.levelapp.annotation.annotations.ChoppOnCreateView;
import com.levelapp.annotation.annotations.ChoppOnDestroy;
import com.levelapp.annotation.annotations.ChoppOnDestroyView;
import com.levelapp.annotation.annotations.ChoppOnPause;
import com.levelapp.annotation.annotations.ChoppOnRestoreState;
import com.levelapp.annotation.annotations.ChoppOnResume;
import com.levelapp.annotation.annotations.ChoppOnSaveState;
import com.levelapp.annotation.annotations.ChoppOnStart;
import com.levelapp.annotation.annotations.ChoppOnStop;
import com.levelapp.annotation.annotations.ChoppOnViewCreated;
import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by rafaldziuryk on 18.01.17.
 */

public enum LifecycleProperty {

  ON_CREATE(ParameterHelper.BUNDLE_MAP, ChoppOnCreate.class, "onCreate"),
  ON_START(ParameterHelper.EMPTY_MAP, ChoppOnStart.class, "onStart"),
  ON_RESUME(ParameterHelper.EMPTY_MAP, ChoppOnResume.class, "onResume"),
  ON_PAUSE(ParameterHelper.EMPTY_MAP, ChoppOnPause.class, "onPause"),
  ON_STOP(ParameterHelper.EMPTY_MAP, ChoppOnStop.class, "onStop"),
  ON_DESTROY(ParameterHelper.EMPTY_MAP, ChoppOnDestroy.class, "onDestroy"),
  ON_DESTROY_VIEW(ParameterHelper.EMPTY_MAP, ChoppOnDestroyView.class, "onDestroyView"),
  ON_CREATE_VIEW(ParameterHelper.BUNDLE_MAP, ChoppOnCreateView.class, "onCreateView"),
  ON_VIEW_CREATED(ParameterHelper.BUNDLE_MAP, ChoppOnViewCreated.class, "onViewCreated"),
  ON_SAVE_INSTANCE(ParameterHelper.BUNDLE_MAP, ChoppOnSaveState.class, "onSaveInstanceState"),
  ON_RESTORE_INSTANCE(ParameterHelper.BUNDLE_MAP, ChoppOnRestoreState.class, "onRestoreInstanceState"),
  CHOPP(ParameterHelper.EMPTY_MAP, Chopp.class, "chopp");


  public Map<Class<?>, String> getParameters() {
    return parameters;
  }

  public Class<? extends Annotation> getAnnotation() {
    return annotation;
  }

  public String getMethodName() {
    return methodName;
  }

  Map<Class<?>, String> parameters;
  Class<? extends Annotation> annotation;
  String methodName;

  LifecycleProperty(
      Map<Class<?>, String> parameters,
      Class<? extends Annotation> annotation,
      String methodName) {
    this.parameters = parameters;
    this.annotation = annotation;
    this.methodName = methodName;
  }
}
