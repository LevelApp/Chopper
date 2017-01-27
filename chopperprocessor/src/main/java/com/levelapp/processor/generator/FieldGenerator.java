package com.levelapp.processor.generator;

import static com.levelapp.processor.utils.ModificatorHelper.isValidField;

import com.levelapp.processor.model.AnnotatedFields;
import com.levelapp.processor.model.ChopperElement;
import com.levelapp.processor.utils.AnnotationHelper;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Created by rafaldziuryk on 27.01.17.
 */

public class FieldGenerator {
  public static void prepareInheritanceMap(ProcessingEnvironment processingEnvironment, Map<TypeElement, AnnotatedFields> annotatedFieldSet) {
    for (Entry<TypeElement, AnnotatedFields> entryBase : annotatedFieldSet.entrySet()) {
      for (Entry<TypeElement, AnnotatedFields> entryCheck : annotatedFieldSet.entrySet()) {
        Types types = processingEnvironment.getTypeUtils();
        TypeMirror baseMirror = entryBase.getKey().asType();
        TypeMirror checkMirror = entryCheck.getKey().asType();
        if (types.isSubtype(baseMirror, checkMirror) && !types
            .isSameType(baseMirror, checkMirror)) {
          Map<Integer, List<ChopperElement>> checkVariable = entryCheck.getValue().getVariableElements();
          entryBase.getValue().addVariableElement(checkVariable);
        }
      }
    }
  }

  public static void prepareSimpleMap(RoundEnvironment roundEnv,
      Messager messager,
      Map<TypeElement, AnnotatedFields> annotatedFieldSet,
      Class<? extends Annotation> annotation) {
    for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
      VariableElement variableElement = (VariableElement) element;
      if (!isValidField(variableElement)) {
        messager.printMessage(Diagnostic.Kind.ERROR,
            "Classes annotated with Chopper cannot be private or primitive", element);
        continue;
      }

      try {
        TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();
        if (!annotatedFieldSet.containsKey(classElement)) {
          annotatedFieldSet.put(classElement, new AnnotatedFields(classElement));
        }
        Map<Integer, List<ChopperElement>> chopperElements = AnnotationHelper
            .levels(messager, variableElement);
        AnnotatedFields fields = annotatedFieldSet.get(classElement);
        fields.addVariableElement(chopperElements);
      } catch (Throwable e) {
        String message = String.format("Couldn't process class %s: %s", variableElement,
            e.getMessage());
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
        e.printStackTrace();
      }
    }
  }
}
