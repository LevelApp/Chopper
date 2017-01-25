package com.levelapp.processor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

class AnnotatedFields {
    final Map<Class<? extends Annotation>, List<VariableElement>> variableElements;
    final Element typeElement;

    AnnotatedFields(Element typeElement) {
        this.typeElement = typeElement;
        this.variableElements = new HashMap<>();
    }

    void addVariableElement(VariableElement element, Class<? extends Annotation> annotation){
        if (!variableElements.containsKey(annotation)){
            variableElements.put(annotation, new ArrayList<VariableElement>());
        }
        if (!variableElements.get(annotation).contains(element)) {
            variableElements.get(annotation).add(element);
        }
    }

    void addVariableElement(List<VariableElement> elements, Class<? extends Annotation> annotation){
        for (VariableElement variableElement : elements){
            addVariableElement(variableElement, annotation);
        }
    }

    public TypeMirror getType() {
        return typeElement.asType();
    }
}