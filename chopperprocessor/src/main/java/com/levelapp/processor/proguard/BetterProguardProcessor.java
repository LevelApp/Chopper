package com.levelapp.processor.proguard;

import com.levelapp.annotation.betterproguard.BetterProguard;
import com.levelapp.annotation.chopperable.Chopperable;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.util.Set;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by rafaldziuryk on 12.01.17.
 */

public class BetterProguardProcessor {

  public void generateBetterProguard(
      Set<TypeElement> keySet,
      ProcessingEnvironment processingEnvironment) {
    try {
      generate(keySet, processingEnvironment);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void generate(
      Set<TypeElement> keySet,
      ProcessingEnvironment processingEnvironment)
      throws IOException {
    TypeSpec generateClass = generateClass(keySet);

    JavaFile javaFile = JavaFile.builder("com.levelapp.betterproguard", generateClass).build();
    javaFile.writeTo(processingEnvironment.getFiler());
  }

  private TypeSpec generateClass(Set<TypeElement> classes) {
    TypeSpec.Builder builder = TypeSpec
        .classBuilder("BetterProguardImpl")
        .addSuperinterface(BetterProguard.class)
        .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
    builder.addMethod(factoryMethod(classes));
    return builder.build();
  }

  private MethodSpec factoryMethod(Set<TypeElement> keySet) {
    StringBuilder builder = new StringBuilder();

    for (TypeElement typeElement : keySet) {
      builder.append("if (");
      builder.append(typeElement.getQualifiedName().toString());
      builder.append(".class.equals(");
      builder.append("instance");
      builder.append("))");
      builder.append("{\n");
      builder.append("return new ");
      builder.append(typeElement.getQualifiedName().toString());
      builder.append("_");
      builder.append(Chopperable.class.getSimpleName());
      builder.append("();\n");
      builder.append("}\n");
    }
    builder.append("return new com.levelapp.annotation.chopperable.EmptyChopperable()");

    return MethodSpec.methodBuilder("getFactory")
        .addAnnotation(Override.class)
        .addModifiers(Modifier.PUBLIC)
        .addParameter(Class.class, "instance")
        .addStatement(builder.toString())
        .returns(Chopperable.class)
        .build();
  }
}
