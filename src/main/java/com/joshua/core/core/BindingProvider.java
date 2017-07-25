package com.joshua.core.core;

import com.google.inject.Binder;
import java.lang.annotation.Annotation;

/**
 * Created by joshua on 2017/7/25.
 */
public interface BindingProvider<AnnotationType extends Annotation, ConfigurationType> extends FacetEnabler {
    void configure(Binder binder, AnnotationType annotation, ApplicationModule<?> module, ConfigurationType configuration);
}
