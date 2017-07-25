package com.joshua.core.core.internal.servlet;

import com.google.inject.Binder;
import com.joshua.core.config.Configuration;
import com.joshua.core.core.ApplicationModule;
import com.joshua.core.core.BindingProvider;
import com.joshua.core.core.Servlet3;


/**
 * Created by joshua on 2017/7/25.
 */
public class Servlet3Enabler implements BindingProvider<Servlet3,Configuration> {
    @Override
    public void configure(Binder binder, Servlet3 annotation, ApplicationModule<?> module, Configuration configuration) {
        final String[] autoScanPackages = new String[]{module.getClass().getPackage().getName()};
        binder.install(new AutoScanningServletModule(annotation.packages().length == 0 ? autoScanPackages : annotation.packages()));
    }
}
