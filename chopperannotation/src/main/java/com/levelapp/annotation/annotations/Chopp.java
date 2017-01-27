package com.levelapp.annotation.annotations;

import com.levelapp.annotation.chopperable.Chopperable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate field with this annotation to auto destruct object
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
@Repeatable(value = Chopps.class)
public @interface Chopp {
  Class<? extends Chopperable>[] value() default {};
  int level() default Chopperable.DEFAULT_LEVEL;
}