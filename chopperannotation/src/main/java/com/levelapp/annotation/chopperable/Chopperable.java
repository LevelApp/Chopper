package com.levelapp.annotation.chopperable;

/**
 * Use this interface to prepare own implementation of object destroying
 * @param <T> is the annotated field
 * @param <E> is enclosed object
 *
 * @author rafaldziuryk on 27.12.16.
 */

public interface Chopperable<T, E> {
  void chopp(T target, E enclosed);
}