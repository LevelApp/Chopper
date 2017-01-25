package com.levelapp.processor;

import com.google.auto.service.AutoService;
import com.levelapp.annotation.Chopper;
import com.levelapp.annotation.EmptyLifecycler;
import com.levelapp.annotation.LifecycleProperty;
import com.levelapp.annotation.annotations.Chopp;
import com.levelapp.annotation.chopperable.Chopperable;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import javax.tools.Diagnostic.Kind;

@AutoService(Processor.class)
public class ChopperProcessor extends AbstractProcessor {

  private static final Kind KIND_LOG = Kind.WARNING;

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
    Set<String> annotations = new HashSet<>();
    for (LifecycleProperty property : LifecycleProperty.values()) {
      annotations.add(property.getAnnotation().getCanonicalName());
    }
    return annotations;
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

    messager.printMessage(KIND_LOG, "Starting generate Chopperable classes");
    makeChopperForAnnotation(roundEnv);

    return true;
  }

  private void makeChopperForAnnotation(RoundEnvironment roundEnv) {
    Map<TypeElement, AnnotatedFields> annotatedFieldSet = new HashMap<>();
    messager.printMessage(KIND_LOG, "Preparing field set");
    for (LifecycleProperty property : LifecycleProperty.values()) {
      prepareSimpleMap(roundEnv, annotatedFieldSet, property.getAnnotation());
    }
    messager.printMessage(KIND_LOG, "Preparing inheritance set");
    prepareInheritanceMap(annotatedFieldSet);
    try {
      messager.printMessage(KIND_LOG, "Start generating classes");
      generate(annotatedFieldSet);
    } catch (IOException e) {
      messager.printMessage(Diagnostic.Kind.ERROR, "Couldn't generate class");
    }

    BetterProguardProcessor betterProguard = new BetterProguardProcessor();
    betterProguard.generateBetterProguard(annotatedFieldSet.keySet(), processingEnvironment);
  }

  private void prepareInheritanceMap(Map<TypeElement, AnnotatedFields> annotatedFieldSet) {
    for (Entry<TypeElement, AnnotatedFields> entryBase : annotatedFieldSet.entrySet()) {
      for (Entry<TypeElement, AnnotatedFields> entryCheck : annotatedFieldSet.entrySet()) {
        Types types = processingEnvironment.getTypeUtils();
        TypeMirror baseMirror = entryBase.getKey().asType();
        TypeMirror checkMirror = entryCheck.getKey().asType();
        if (types.isSubtype(baseMirror, checkMirror) && !types
            .isSameType(baseMirror, checkMirror)) {
          for (LifecycleProperty property : LifecycleProperty.values()) {
            List<VariableElement> baseVariable = entryBase.getValue().variableElements
                .get(property.getAnnotation());
            List<VariableElement> checkVariable = entryCheck.getValue().variableElements
                .get(property.getAnnotation());
            if (checkVariable == null) {
              continue;
            }
            if (baseVariable == null) {
              List<VariableElement> newVariables = new ArrayList<>();
              entryBase.getValue().variableElements.put(property.getAnnotation(), newVariables);
              baseVariable = newVariables;
            }
            baseVariable.addAll(checkVariable);
            messager.printMessage(KIND_LOG,
                "For `" + entryBase.getKey().getSimpleName() + " ` element add `" + entryCheck
                    .getKey().getSimpleName() + "` variables by inheritance for `" + property
                    .getAnnotation().getSimpleName() + "` annotation");
          }
        }
      }
    }
  }

  private void prepareSimpleMap(RoundEnvironment roundEnv,
      Map<TypeElement, AnnotatedFields> annotatedFieldSet,
      Class<? extends Annotation> annotation) {
    for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
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
        fields.addVariableElement(variableElement, annotation);
        fields.addVariableElement(variableElement, Chopp.class);
        messager.printMessage(KIND_LOG,
            "For `" + classElement.getSimpleName() + " ` element add `" + variableElement
                .getSimpleName() + "` to `" + annotation.getSimpleName() + "` Annotation");
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
      messager
          .printMessage(KIND_LOG, "Start generating `" + entry.getKey().getSimpleName() + "`class");
      String packageName = processingEnvironment.getElementUtils().getPackageOf(entry.getKey())
          .getQualifiedName().toString();
      TypeSpec generateClass = generateClass(entry);

      JavaFile javaFile = JavaFile.builder(packageName, generateClass).build();
      javaFile.writeTo(processingEnv.getFiler());
    }
  }

  private boolean isValidField(VariableElement element) {
    if (!hasAccess(element)) {
      String message = "Classes annotated with Chopper cannot be private or primitive.";
      messager.printMessage(Diagnostic.Kind.ERROR, message, element);
      return false;
    }
    return true;
  }

  private boolean hasAccess(VariableElement annotatedField) {
    return !isPrivate(annotatedField) && !isPrimitive(annotatedField);
  }

  private boolean isPrivate(VariableElement annotatedField) {
    return annotatedField.getModifiers().contains(Modifier.PRIVATE);
  }

  private boolean isFinal(VariableElement annotatedField) {
    return annotatedField.getModifiers().contains(Modifier.FINAL);
  }

  private boolean isPrimitive(VariableElement annotatedField) {
    return annotatedField.asType().getKind().isPrimitive();
  }

  public TypeSpec generateClass(final Entry<TypeElement, AnnotatedFields> entry) {
    TypeSpec.Builder builder = TypeSpec
        .classBuilder(
            entry.getValue().typeElement.getSimpleName() + "_" + Chopperable.class.getSimpleName())
        .superclass(EmptyLifecycler.class)
        .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

    for (LifecycleProperty property : LifecycleProperty.values()) {
      List<VariableElement> variables = entry.getValue().variableElements
          .get(property.getAnnotation());
      if (variables != null && !variables.isEmpty()) {
        messager.printMessage(KIND_LOG,
            "Start generating `" + property.getMethodName() + "` method for `" + entry.getKey()
                .getSimpleName() + "`class");
        builder.addMethod(letRollChoppers(entry, property));
      }
    }
    return builder.build();
  }

  private MethodSpec letRollChoppers(Entry<TypeElement, AnnotatedFields> entry,
      LifecycleProperty property) {
    StringBuilder builder = new StringBuilder();
    castInstance(entry, builder);
    builder.append(Chopperable.class.getCanonicalName());
    builder.append(" chopper;");
    builder.append(System.getProperty("line.separator"));
    iterateVariables(entry.getValue().variableElements.get(property.getAnnotation()), builder);

    List<ParameterSpec> parameterSpecs = new ArrayList<>();
    for (Map.Entry<Class<?>, String> parameterEntry : property.getParameters().entrySet()) {
      ParameterSpec parameterSpec = ParameterSpec
          .builder(parameterEntry.getKey(), parameterEntry.getValue()).build();
      parameterSpecs.add(parameterSpec);
    }

    return MethodSpec.methodBuilder(property.getMethodName())
        .addJavadoc("set null to every field annotated with @Chopp")
        .addModifiers(Modifier.PUBLIC)
        .addAnnotation(Override.class)
        .addStatement(builder.toString())
        .addParameter(TypeName.OBJECT, "instance")
        .addParameter(TypeName.OBJECT, "enclosed")
        .addParameters(parameterSpecs)
        .returns(TypeName.VOID)
        .build();
  }

  private void iterateVariables(List<VariableElement> variableElements, StringBuilder builder) {
    if (variableElements != null) {
      for (VariableElement variableElement : variableElements) {
        for (AnnotationMirror annotationMirror : variableElement.getAnnotationMirrors()) {
          Map<? extends ExecutableElement, ? extends AnnotationValue> map = annotationMirror
              .getElementValues();
          choppVariableByAllChoppers(builder, variableElement, map);
        }
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
    if (!isFinal(variableElement)) {
      choppByNull(builder, variableElement);
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
      builder.append(", enclosed");
      builder.append(");");
      builder.append(System.getProperty("line.separator"));
    }
  }

  private void choppByNull(StringBuilder builder, VariableElement variableElement) {
    builder.append(String.format("element.%s = null;", variableElement.getSimpleName()));
    builder.append(System.getProperty("line.separator"));
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
}

