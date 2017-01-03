package com.levelapp.processor;

import com.google.auto.service.AutoService;
import com.levelapp.annotation.Chopp;
import com.levelapp.annotation.Chopper;
import com.levelapp.annotation.Chopperable;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class ChopperProcessor extends AbstractProcessor {

  private static final String ANNOTATION = "@" + Chopp.class.getSimpleName();
  private Messager messager;

  private ProcessingEnvironment processingEnvironment;

  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    processingEnvironment = processingEnv;
    messager = processingEnv.getMessager();
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Collections.singleton(Chopp.class.getCanonicalName());
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    Map<TypeElement, AnnotatedFields> annotatedFieldSet = new HashMap<>();
    prepareSimpleMap(roundEnv, annotatedFieldSet);
    prepareInheritanceMap(annotatedFieldSet);
    try {
      generate(annotatedFieldSet);
    } catch (IOException e) {
      messager.printMessage(Diagnostic.Kind.ERROR, "Couldn't generate class");
    }

    return true;
  }

  private void prepareInheritanceMap(Map<TypeElement, AnnotatedFields> annotatedFieldSet) {
    for (Entry<TypeElement, AnnotatedFields> entryBase : annotatedFieldSet.entrySet()) {
      for (Entry<TypeElement, AnnotatedFields> entryCheck : annotatedFieldSet.entrySet()) {
        Types types = processingEnvironment.getTypeUtils();
        TypeMirror baseMirror = entryBase.getKey().asType();
        TypeMirror checkMirror = entryCheck.getKey().asType();
        if (types.isSubtype(baseMirror, checkMirror)) {
          entryBase.getValue().addVariableElement(entryCheck.getValue().variableElements);
        }
      }
    }
  }

  private void prepareSimpleMap(RoundEnvironment roundEnv,
      Map<TypeElement, AnnotatedFields> annotatedFieldSet) {
    for (Element element : roundEnv.getElementsAnnotatedWith(Chopp.class)) {
      VariableElement variableElement = (VariableElement) element;
      if (!isValidField(variableElement)) {
        continue;
      }

      try {
        TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();
        if (!annotatedFieldSet.containsKey(classElement)) {
          annotatedFieldSet.put(classElement, new AnnotatedFields(classElement));
        }

        AnnotatedFields fields = annotatedFieldSet.get(classElement);
        fields.addVariableElement(variableElement);
      } catch (Throwable e) {
        String message = String.format("Couldn't process class %s: %s", variableElement,
            e.getMessage());
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
        e.printStackTrace();
      }
    }
  }

  private void generate(Map<TypeElement, AnnotatedFields> classList) throws IOException {
    if (null == classList || classList.size() == 0) {
      return;
    }

    for (Entry<TypeElement, AnnotatedFields> entry : classList.entrySet()) {
      String packageName = processingEnvironment.getElementUtils().getPackageOf(entry.getKey())
          .getQualifiedName().toString();
      TypeSpec generateClass = generateClass(entry);

      JavaFile javaFile = JavaFile.builder(packageName, generateClass).build();
      javaFile.writeTo(processingEnv.getFiler());
    }
  }


  private boolean isValidField(VariableElement element) {
    if (!hasAccess(element)) {
      String message = String.format("Classes annotated with %s must be public.",
          ANNOTATION);
      messager.printMessage(Diagnostic.Kind.ERROR, message, element);
      return false;
    }
    return true;
  }

  private boolean hasAccess(VariableElement annotatedClass) {
    return !isFinal(annotatedClass) && !isPrivate(annotatedClass);
  }

  private boolean isFinal(VariableElement annotatedClass) {
    return annotatedClass.getModifiers().contains(Modifier.FINAL);
  }

  private boolean isPrivate(VariableElement annotatedClass) {
    return annotatedClass.getModifiers().contains(Modifier.PRIVATE);
  }

  public TypeSpec generateClass(final Entry<TypeElement, AnnotatedFields> entry) {
    TypeSpec.Builder builder = TypeSpec
        .classBuilder(entry.getValue().typeElement.getSimpleName() + Chopper.CHOPPER_SUFFIX)
        .addSuperinterface(Chopperable.class)
        .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
    builder.addMethod(letRollNuller(entry));
    builder.addMethod(letRollChoppers(entry));
    builder.addMethod(chopp());
    return builder.build();
  }

  private MethodSpec letRollChoppers(Entry<TypeElement, AnnotatedFields> entry) {
    StringBuilder builder = new StringBuilder();
    castInstance(entry, builder);
    builder.append(Chopperable.class.getSimpleName());
    builder.append(" chopper;");
    builder.append(System.getProperty("line.separator"));
    iterateVariables(entry, builder);

    return MethodSpec.methodBuilder("chopper")
        .addJavadoc("set null to every field annotated with @Chopp")
        .addModifiers(Modifier.PUBLIC)
        .addStatement(builder.toString())
        .addParameter(TypeName.OBJECT, "instance")
        .returns(TypeName.VOID)
        .build();
  }

  private void iterateVariables(Entry<TypeElement, AnnotatedFields> entry, StringBuilder builder) {
    for (VariableElement variableElement : entry.getValue().variableElements) {
      for (AnnotationMirror annotationMirror : variableElement.getAnnotationMirrors()) {
        Map<? extends ExecutableElement, ? extends AnnotationValue> map = annotationMirror
            .getElementValues();
        choppVariableByAllChoppers(builder, variableElement, map);
      }
    }
  }

  private void choppVariableByAllChoppers(StringBuilder builder, VariableElement variableElement,
      Map<? extends ExecutableElement, ? extends AnnotationValue> map) {
    AnnotationValue chopper;
    for (ExecutableElement e : map.keySet()) {
      if (Chopper.CHOPPER_PROPERTY.equals(e.getSimpleName().toString())) {
        chopper = map.get(e);
        if (chopper != null) {
          List<Object> annotatedClasses = (List<Object>) chopper.getValue();
          choppBySingleChopper(builder, variableElement, annotatedClasses);
        }
      }
    }
  }

  private void choppBySingleChopper(StringBuilder builder, VariableElement variableElement,
      List<Object> annotatedClasses) {
    for (Object annotatedClass : annotatedClasses) {
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
      builder.append(variableElement.getSimpleName());
      builder.append(");");
      builder.append(System.getProperty("line.separator"));
    }
  }

  /**
   * @return a createString() method that takes annotatedFields's type as an input.
   */
  private MethodSpec letRollNuller(Entry<TypeElement, AnnotatedFields> entry) {
    StringBuilder builder = new StringBuilder();
    castInstance(entry, builder);

    for (VariableElement variableElement : entry.getValue().variableElements) {
      for (AnnotationMirror annotationMirror : variableElement.getAnnotationMirrors()) {
        Map<? extends ExecutableElement, ? extends AnnotationValue> map = annotationMirror
            .getElementValues();
        AnnotationValue chopper;
        boolean setNull = true;
        for (ExecutableElement e : map.keySet()) {
          if (Chopper.SET_NULL_PROPERTY.equals(e.getSimpleName().toString())) {
            chopper = map.get(e);
            if (chopper != null) {
              setNull = (boolean) chopper.getValue();
            }
          }
        }
        if (setNull) {
          builder.append(String.format("element.%s = null;", variableElement.getSimpleName()));
          builder.append(System.getProperty("line.separator"));
        }
      }

    }

    return MethodSpec.methodBuilder("nuller")
        .addJavadoc("set null to every field annotated with @Chopp")
        .addModifiers(Modifier.PUBLIC)
        .addParameter(TypeName.OBJECT, "instance")
        .addStatement(builder.toString())
        .returns(TypeName.VOID)
        .build();
  }

  private void castInstance(Entry<TypeElement, AnnotatedFields> entry, StringBuilder builder) {
    String className = entry.getKey().getSimpleName().toString();
    builder.append(className);
    builder.append(" element = ");
    builder.append("(");
    builder.append(className);
    builder.append(") ");
    builder.append("instance;");
    builder.append(System.getProperty("line.separator"));
  }

  private MethodSpec chopp() {
    StringBuilder builder = new StringBuilder();
    builder.append("chopper(instance);");
    builder.append(System.getProperty("line.separator"));
    builder.append("nuller(instance)");

    return MethodSpec.methodBuilder("chopp")
        .addJavadoc("set null to every field annotated with @Chopp")
        .addModifiers(Modifier.PUBLIC)
        .addAnnotation(Override.class)
        .addParameter(TypeName.OBJECT, "instance")
        .addStatement(builder.toString())
        .returns(TypeName.VOID)
        .build();
  }
}

