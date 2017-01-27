package com.levelapp.processor.utils;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

/**
 * Created by rafaldziuryk on 27.01.17.
 */

public class ModificatorHelper {

  public static boolean isValidField(VariableElement element) {
    return hasAccess(element);
  }

  public static boolean isFinal(VariableElement annotatedField) {
    return annotatedField.getModifiers().contains(Modifier.FINAL);
  }

  private static boolean hasAccess(VariableElement annotatedField) {
    return !isPrivate(annotatedField) && !isPrimitive(annotatedField);
  }

  private static boolean isPrivate(VariableElement annotatedField) {
    return annotatedField.getModifiers().contains(Modifier.PRIVATE);
  }

  private static boolean isPrimitive(VariableElement annotatedField) {
    return annotatedField.asType().getKind().isPrimitive();
  }
}
