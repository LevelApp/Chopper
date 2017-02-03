package com.levelapp.processor.generator;

import static com.levelapp.processor.generator.MethodGenerator.letRollChoppers;

import com.levelapp.annotation.chopperable.Chopperable;
import com.levelapp.annotation.chopperable.EmptyChopperable;
import com.levelapp.processor.model.AnnotatedFields;
import com.levelapp.processor.model.ChopperElement;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by rafaldziuryk on 27.01.17.
 */

public class ClassGenerator {

  public static void generate(Map<TypeElement, AnnotatedFields> classList, ProcessingEnvironment processingEnvironment) throws IOException {
    if (null == classList || classList.size() == 0) {
      return;
    }
    for (Entry<TypeElement, AnnotatedFields> entry : classList.entrySet()) {
      String packageName = processingEnvironment.getElementUtils().getPackageOf(entry.getKey())
          .getQualifiedName().toString();
      TypeSpec generateClass = generateClass(entry);
      JavaFile javaFile = JavaFile.builder(packageName, generateClass).build();
      javaFile.writeTo(processingEnvironment.getFiler());
    }
  }


  private static TypeSpec generateClass(final Entry<TypeElement, AnnotatedFields> entry) {
    TypeSpec.Builder builder = TypeSpec
        .classBuilder(
            entry.getValue().getTypeElement().getSimpleName() + "_" + Chopperable.class.getSimpleName())
        .superclass(EmptyChopperable.class)
        .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

    Map<Integer, List<ChopperElement>> variables = entry.getValue().getVariableElements();
    if (!variables.isEmpty()) {
      builder.addMethod(letRollChoppers(entry));
    }
    return builder.build();
  }
}
