package com.levelapp.processor;

import com.levelapp.annotation.betterproguard.BetterProguard;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
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

  private final Class<?> betterInterface;
  private final Class<?> betterBaseInterface;

  public BetterProguardProcessor(Class<?> betterInterface, Class<?> betterBaseInterface) {
    this.betterInterface = betterInterface;
    this.betterBaseInterface = betterBaseInterface;
  }

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
        .classBuilder("BetterProguardImpl" + "_" + betterInterface.getSimpleName())
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
      builder.append("instance.getClass()");
      builder.append("))");
      builder.append("{\n");
      builder.append("return new ");
      builder.append(typeElement.getQualifiedName().toString());
      builder.append("_");
      builder.append(betterInterface.getSimpleName());
      builder.append("();\n");
      builder.append("}\n");
    }
    builder.append("return new com.levelapp.annotation.chopperable.EmptyChopperable()");

    return MethodSpec.methodBuilder("getFactory")
        .addAnnotation(Override.class)
        .addModifiers(Modifier.PUBLIC)
        .addParameter(TypeName.OBJECT, "instance")
        .addStatement(builder.toString())
        .returns(betterBaseInterface)
        .build();
  }
}
