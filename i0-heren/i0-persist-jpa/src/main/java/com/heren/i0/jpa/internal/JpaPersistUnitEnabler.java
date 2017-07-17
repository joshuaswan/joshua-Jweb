package com.heren.core.jpa.internal;

import com.google.inject.Binder;
import com.google.inject.persist.jpa.HerenJpaPersistModule;
import com.google.inject.servlet.ServletModule;
import com.heren.core.core.ApplicationModule;
import com.heren.core.core.BindingProvider;
import com.heren.core.core.StartupTasks;
import com.heren.core.jpa.JpaInitializer;
import com.heren.core.jpa.JpaPersist;
import com.heren.core.jpa.PersistFilter;
import com.heren.core.jpa.WithDatabase;
import com.heren.core.jpa.internal.migration.Migration;

import static com.google.common.base.Preconditions.checkArgument;

public class JpaPersistUnitEnabler implements StartupTasks<JpaPersist, WithDatabase>, BindingProvider<JpaPersist, WithDatabase> {

    @Override
    public void perform(JpaPersist annotation, WithDatabase configuration) {
        if (configuration.getDatabase() != null) Migration.migrate(configuration.getDatabase());
    }

    @Override
    public void configure(Binder binder, JpaPersist annotation, ApplicationModule<?> module, WithDatabase configuration) {
        checkArgument(configuration.getDatabase() != null, "No database configuration found");
        final String[] autoScanPackages = new String[]{module.getClass().getPackage().getName()};
        // FIXME: 2016/10/22 采用自己的PersistModule,实现Entity自动扫描
        binder.install(new HerenJpaPersistModule(annotation.unit()).properties(configuration.getDatabase().toProperties()).packages(autoScanPackages));
//        binder.install(new JpaPersistModule(annotation.unit()).properties(configuration.getDatabase().toProperties()));
        binder.bind(JpaInitializer.class).asEagerSingleton();
        binder.install(new ServletModule() {
            @Override
            protected void configureServlets() {
                filter("/*").through(PersistFilter.class);
            }
        });

    }
}
