package com.levelapp.processor.generator;

import static com.levelapp.processor.utils.ModificatorHelper.isFinal;

import com.levelapp.annotation.Chopper;
import com.levelapp.annotation.chopperable.Chopperable;
import com.levelapp.processor.model.AnnotatedFields;
import com.levelapp.processor.model.ChopperElement;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by rafaldziuryk on 27.01.17.
 */

class MethodGenerator {

  static MethodSpec letRollChoppers(Entry<TypeElement, AnnotatedFields> entry) {
    StringBuilder builder = new StringBuilder();
    castInstance(entry, builder);
    builder.append(Chopperable.class.getCanonicalName());
    builder.append(" chopper;");
    builder.append(System.getProperty("line.separator"));
    iterateVariables(entry.getValue().getVariableElements(), builder);

    return MethodSpec.methodBuilder("chopp")
        .addJavadoc("set null to every field annotated with @Chopp")
        .addModifiers(Modifier.PUBLIC)
        .addAnnotation(Override.class)
        .addStatement(builder.toString())
        .addParameter(TypeName.OBJECT, "instance")
        .addParameter(TypeName.OBJECT, "enclosed")
        .addParameter(TypeName.INT, "level")
        .returns(TypeName.VOID)
        .build();
  }

  private static void iterateVariables(Map<Integer, List<ChopperElement>> variableElements,
      StringBuilder builder) {
    for (Map.Entry<Integer, List<ChopperElement>> entry : variableElements.entrySet()) {
      builder.append(System.getProperty("line.separator"));
      builder.append("if (level <= ");
      builder.append(entry.getKey());
      builder.append(")");
      builder.append(System.getProperty("line.separator"));
      builder.append("{");
      builder.append(System.getProperty("line.separator"));
      choppVariableByAllChoppers(builder, entry.getValue());
      builder.append("}");

    }
  }

  private static void choppVariableByAllChoppers(StringBuilder builder,
      List<ChopperElement> choppersElement) {
    for (ChopperElement chopperElement : choppersElement) {
      choppBySingleChopper(builder, chopperElement);
      if (!isFinal(chopperElement.getElement())) {
        choppByNull(builder, chopperElement);
      }
    }
  }

  private static void choppBySingleChopper(StringBuilder builder, ChopperElement chopperElement) {
    for (Object annotatedClass : chopperElement.getChopperClass()) {
      builder.append("chopper = ");
      builder.append(Chopper.class.getCanonicalName());
      builder.append(".");
      builder.append("safeChopperableClass");
      builder.append("(");
      builder.append(annotatedClass);
      builder.append(");");
      builder.append(System.getProperty("line.separator"));
      builder.append("chopper.chopp(");
      builder.append("element.");
      builder.append(chopperElement.getElement().getSimpleName());
      builder.append(", enclosed, level");
      builder.append(");");
      builder.append(System.getProperty("line.separator"));
    }
  }

  private static void choppByNull(StringBuilder builder, ChopperElement chopperElement) {
    builder.append(String.format("element.%s = null;", chopperElement.getElement().getSimpleName()));
    builder.append(System.getProperty("line.separator"));
  }

  private static void castInstance(Entry<TypeElement, AnnotatedFields> entry, StringBuilder builder) {
    String className = entry.getKey().getSimpleName().toString();
    builder.append(className);
    builder.append(" element = ");
    builder.append("(");
    builder.append(className);
    builder.append(") ");
    builder.append("instance;");
    builder.append(System.getProperty("line.separator"));
  }
}
