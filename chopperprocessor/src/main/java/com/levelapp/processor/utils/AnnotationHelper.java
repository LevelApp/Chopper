package com.levelapp.processor.utils;

import com.levelapp.annotation.Chopper;
import com.levelapp.annotation.annotations.Chopp;
import com.levelapp.annotation.annotations.Chopps;
import com.levelapp.processor.model.ChopperElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

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
//        Map<? extends ExecutableElement, ? extends AnnotationValue> map = annotationMirror
//            .getElementValues();
//        for (ExecutableElement e : map.keySet()) {
//          AnnotationValue annotationValue = map.get(e);
//          List chopps = (List) annotationValue.getValue();
//          messager.printMessage(ChopperProcessor.KIND_LOG, chopps.get(0).getClass().getCanonicalName());
//          for (Chopp chopp : chopps) {
//            if (!objects.containsKey(chopp.level())) {
//              objects.put(chopp.level(), new ArrayList<ChopperElement>());
//            }
//            objects.get(chopp.level()).add(new ChopperElement(variableElement, chopp.value()));
//          }
//        }

      } else if (Chopp.class.getSimpleName()
          .equals(annotationMirror.getAnnotationType().asElement().getSimpleName().toString())) {
//        single annotation
        Map<? extends ExecutableElement, ? extends AnnotationValue> map = annotationMirror
            .getElementValues();
        ChopperElement chopperElement = new ChopperElement();
        chopperElement.setElement(variableElement);
        int level = 100;
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
}
