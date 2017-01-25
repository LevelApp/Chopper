package com.levelapp.annotation;

/**
 * Created by rafaldziuryk on 06.12.16.
 */

public interface Lifecycler{

  void onCreate(Object instance, Object enclosed, android.os.Bundle savedInstanceState);

  void onStart(Object instance, Object enclosed);

  void onResume(Object instance, Object enclosed);

  void onPause(Object instance, Object enclosed);

  void onStop(Object instance, Object enclosed);

  void onDestroy(Object instance, Object enclosed);

  void onCreateView(Object instance, Object enclosed, android.os.Bundle savedInstanceState);

  void onDestroyView(Object instance, Object enclosed);

  void onViewCreated(Object instance, Object enclosed, android.os.Bundle savedInstanceState);

  void onSaveInstanceState(Object instance, Object enclosed, android.os.Bundle outState);

  void onRestoreInstanceState(Object instance, Object enclosed, android.os.Bundle savedInstanceState);

  void chopp(Object instance, Object enclosed);
}