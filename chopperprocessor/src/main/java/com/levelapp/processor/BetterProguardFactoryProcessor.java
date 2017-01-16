package com.levelapp.processor;

import com.levelapp.annotation.betterproguard.BetterProguard;
import com.levelapp.annotation.betterproguard.BetterProguardFactory;
import com.levelapp.annotation.chopperable.Chopperable;
import com.levelapp.annotation.chopperable.ChopperableOnDestroy;
import com.levelapp.annotation.chopperable.ChopperableOnDestroyView;
import com.levelapp.annotation.chopperable.ChopperableOnPause;
import com.levelapp.annotation.chopperable.ChopperableOnStop;
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

public class BetterProguardFactoryProcessor {

  public void generateBetterProguard(ProcessingEnvironment processingEnvironment) {
    try {
      generate(processingEnvironment);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void generate(ProcessingEnvironment processingEnvironment)
      throws IOException {
    TypeSpec generateClass = generateClass();

    JavaFile javaFile = JavaFile.builder("com.levelapp.betterproguard", generateClass).build();
    javaFile.writeTo(processingEnvironment.getFiler());
  }

  private TypeSpec generateClass() {
    TypeSpec.Builder builder = TypeSpec
        .classBuilder("BetterProguardFactoryImpl")
        .addSuperinterface(BetterProguardFactory.class)
        .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
    builder.addMethod(factoryMethod(ChopperableOnPause.class));
    builder.addMethod(factoryMethod(ChopperableOnStop.class));
    builder.addMethod(factoryMethod(ChopperableOnDestroyView.class));
    builder.addMethod(factoryMethod(ChopperableOnDestroy.class));
    return builder.build();
  }

  private MethodSpec factoryMethod(Class<? extends Chopperable> impl) {
    String statement = "return new com.levelapp.betterproguard.BetterProguardImpl_" + impl.getSimpleName() + "()";
    String simpleName = impl.getSimpleName();
    String methodName = Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);

    return MethodSpec.methodBuilder(methodName)
        .addAnnotation(Override.class)
        .addModifiers(Modifier.PUBLIC)
        .addStatement(statement)
        .returns(BetterProguard.class)
        .build();
  }
}
