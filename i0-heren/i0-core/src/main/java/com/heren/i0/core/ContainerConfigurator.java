package com.heren.core.core;

import com.heren.core.config.Configuration;

import java.lang.annotation.Annotation;

public interface ContainerConfigurator<AnnotationType extends Annotation, ConfigurationType extends Configuration, ContainerType extends ServletContainer> extends FacetEnabler {
    void configure(ContainerType container, AnnotationType annotation, ApplicationModule<ConfigurationType> module) throws Exception;
}
