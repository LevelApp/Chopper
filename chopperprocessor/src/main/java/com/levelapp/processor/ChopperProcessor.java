package com.levelapp.processor;

import com.google.auto.service.AutoService;
import com.levelapp.annotation.annotations.Chopp;
import com.levelapp.annotation.annotations.Chopps;
import com.levelapp.processor.generator.ClassGenerator;
import com.levelapp.processor.generator.FieldGenerator;
import com.levelapp.processor.model.AnnotatedFields;
import com.levelapp.processor.proguard.BetterProguardProcessor;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
class ChopperProcessor extends AbstractProcessor {

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
    annotations.add(Chopp.class.getCanonicalName());
//    JAVA 1.8 compatibilty
    annotations.add(Chopps.class.getCanonicalName());
    return annotations;
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    makeChopperForAnnotation(roundEnv);
    return true;
  }

  private void makeChopperForAnnotation(RoundEnvironment roundEnv) {
    Map<TypeElement, AnnotatedFields> annotatedFieldSet = new HashMap<>();
    FieldGenerator.prepareSimpleMap(roundEnv, messager, annotatedFieldSet, Chopp.class);
    FieldGenerator.prepareSimpleMap(roundEnv, messager, annotatedFieldSet, Chopps.class);
    FieldGenerator.prepareInheritanceMap(processingEnvironment, annotatedFieldSet);
    try {
      ClassGenerator.generate(annotatedFieldSet, processingEnvironment);
    } catch (IOException e) {
      messager.printMessage(Diagnostic.Kind.ERROR, "Couldn't generate class");
    }
    BetterProguardProcessor betterProguard = new BetterProguardProcessor();
    betterProguard.generateBetterProguard(annotatedFieldSet.keySet(), processingEnvironment);
  }
}