package com.levelapp.processor.utils;

import com.levelapp.annotation.Chopper;
import com.levelapp.annotation.annotations.Chopp;
import com.levelapp.annotation.annotations.Chopps;
import com.levelapp.annotation.chopperable.Chopperable;
import com.levelapp.processor.model.ChopperElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic.Kind;

/**
 * Created by rafaldziuryk on 27.01.17.
 */

public class AnnotationHelper {

  public static Map<Integer, List<ChopperElement>> levels(Messager messager,
      VariableElement variableElement) {
    Map<Integer, List<ChopperElement>> objects = new TreeMap<>();
    for (AnnotationMirror annotationMirror : variableElement.getAnnotationMirrors()) {
      if (Chopps.class.getSimpleName()
          .equals(annotationMirror.getAnnotationType().asElement().getSimpleName().toString())) {
//                repeatable annotation
        Chopps chopps = variableElement.getAnnotation(Chopps.class);
        Chopp[] choppers = chopps.value();
        for (Chopp chopp : choppers) {
          ChopperElement chopperElement = new ChopperElement();
          chopperElement.setElement(variableElement);
          List<? extends TypeMirror> typeMirror = getValues(chopp, messager);
          messager.printMessage(Kind.WARNING, typeMirror.toString());
//          List classList = Arrays.asList(chopp.value());
//          chopperElement.setChopperClass(classList);
        }
      } else if (Chopp.class.getSimpleName()
          .equals(annotationMirror.getAnnotationType().asElement().getSimpleName().toString())) {
//        single annotation
        Map<? extends ExecutableElement, ? extends AnnotationValue> map = annotationMirror
            .getElementValues();
        ChopperElement chopperElement = new ChopperElement();
        chopperElement.setElement(variableElement);
        int level = Chopperable.DEFAULT_LEVEL;
        for (ExecutableElement e : map.keySet()) {
          if (Chopper.CHOPPER_PROPERTY.equals(e.getSimpleName().toString())) {
            AnnotationValue chopper = map.get(e);
            if (chopper != null) {
              List annotatedClasses = (List) chopper.getValue();
              chopperElement.setChopperClass(annotatedClasses);
            }
          } else if (Chopper.LEVEL_PROPERTY.equals(e.getSimpleName().toString())) {
            AnnotationValue chopper = map.get(e);
            level = (int) chopper.getValue();
          }
        }
        if (!objects.containsKey(level)) {
          objects.put(level, new ArrayList());
        }
        objects.get(level).add(chopperElement);
      }
    }
    return objects;
  }

  private static List<? extends TypeMirror> getValues(Chopp annotation, Messager messager) {
    try {
      annotation.value(); // this should throw
    } catch (MirroredTypesException mte) {
      return mte.getTypeMirrors();
    } catch (Throwable e){
      messager.printMessage(Kind.WARNING, e.toString());
    }
    return null; // can this ever happen ??
  }
}
