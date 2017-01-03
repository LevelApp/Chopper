package com.levelapp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate field with this annotation to auto destruct object
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Chopp {
  Class<? extends Chopperable>[] chopper() default {};
  boolean setNull() default true;
}