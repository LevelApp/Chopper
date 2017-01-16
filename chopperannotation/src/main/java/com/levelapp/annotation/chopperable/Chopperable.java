package com.levelapp.annotation.chopperable;

import com.levelapp.annotation.Lifecycle;

/**
 * Use this interface to prepare own implementation of object destroying
 * @param <T> is the annotated field
 * @param <E> is enclosed object
 *
 * @author rafaldziuryk on 27.12.16.
 */

public interface Chopperable<T, E> {
  void chopp(T target, E enclosed, Lifecycle lifecycle);
}