package com.example.customprocessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface CustomAnnotation {
    String page();

    String key();

    String action();

    String name();

    String values();

    String value();

    String canUse();

}
