package com.levelapp.processor.model;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.VariableElement;

/**
 * Created by rafaldziuryk on 27.01.17.
 */

public class ChopperElement {

  private VariableElement element;
  private List<Class> chopperClass = new ArrayList<>();

  public ChopperElement() {
  }

  public ChopperElement(VariableElement element, List<Class> chopperClass) {
    this.element = element;
    this.chopperClass = chopperClass;
  }

  public VariableElement getElement() {
    return element;
  }

  public ChopperElement setElement(VariableElement element) {
    this.element = element;
    return this;
  }

  public List<Class> getChopperClass() {
    return chopperClass;
  }

  public ChopperElement setChopperClass(List<Class> chopperClass) {
    this.chopperClass = chopperClass;
    return this;
  }
}
