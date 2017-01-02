package com.levelapp.processor;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

class AnnotatedFields {
    public final List<VariableElement> variableElements;
    public final Element typeElement;

    public AnnotatedFields(Element typeElement) {
        this.typeElement = typeElement;
        this.variableElements = new ArrayList<>();
    }

    public void addVariableElement(VariableElement element){
        if (!variableElements.contains(element)) {
            variableElements.add(element);
        }
    }

    public void addVariableElement(List<VariableElement> elements){
        for (VariableElement variableElement : elements){
            addVariableElement(variableElement);
        }
    }

    public TypeMirror getType() {
        return typeElement.asType();
    }
}