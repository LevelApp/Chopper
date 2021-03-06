package com.levelapp.annotation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate field with this annotation to auto destruct object
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Chopps {
  Chopp[] value() default {};
}