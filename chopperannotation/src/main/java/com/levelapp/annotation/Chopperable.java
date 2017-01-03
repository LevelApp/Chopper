package com.levelapp.annotation;

/**
 * Use this interface to prepare own implementation of object destroying
 *
 * Created by rafaldziuryk on 27.12.16.
 */

public interface Chopperable<T> {
  void chopp(T chopp);
}
