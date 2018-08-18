package com.example.customprocessor;

import com.squareup.javapoet.JavaFile;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
@SupportedAnnotationTypes("com.example.customprocessor.CustomAnnotation")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class CustomProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(CustomAnnotation.class);
        for (Element element : elements) {

        }
        JavaFile.builder(CustomProcessor.class.getPackage().getName(),).build();
        return false;
    }
}
