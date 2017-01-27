package com.levelapp.processor.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

public class AnnotatedFields {

  private final Map<Integer, List<ChopperElement>> variableElements;
  private final Element typeElement;

  public AnnotatedFields(Element typeElement) {
    this.typeElement = typeElement;
    this.variableElements = new TreeMap<>();
  }

  private void addVariableElement(ChopperElement element, int level) {
    if (!variableElements.containsKey(level)) {
      variableElements.put(level, new ArrayList<ChopperElement>());
    }
    if (!variableElements.get(level).contains(element)) {
      variableElements.get(level).add(element);
    }
  }

  public void addVariableElement(Map<Integer, List<ChopperElement>> elements) {
    for (Map.Entry<Integer, List<ChopperElement>> element : elements.entrySet()) {
      int level = element.getKey();
      for (ChopperElement variableElement : element.getValue()) {
        addVariableElement(variableElement, level);

      }
    }
  }

  public Map<Integer, List<ChopperElement>> getVariableElements() {
    return variableElements;
  }

  public Element getTypeElement() {
    return typeElement;
  }

  public TypeMirror getType() {
    return typeElement.asType();
  }
}