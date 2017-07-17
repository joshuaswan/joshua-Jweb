package com.heren.core.gradle.facets

import com.heren.core.gradle.core.ApplicationFacet
import com.heren.core.gradle.facets.puppet.Module
import com.heren.core.gradle.facets.puppet.ModuleSet
import org.gradle.api.Project

class Java implements ApplicationFacet {
    String version = "1.7"
    String guava = "13.0.1"

    @Override
    void generateScaffold(Project project) {
    }

    @Override
    void configure(Project project) {
        project.dependencies {
            project.extensions.sourceCompatibility = version

            project.dependencies {
                compile "com.google.guava:guava:$guava"
            }
        }

        for (environment in project.environments)
            if (project.provisioner.configure(this, environment))
                project.logger.info("Java enabled for $environment.name")
    }

    def puppet(ModuleSet modules) {
        modules.add(Module.of('puppetlabs/stdlib', ""))
        modules.add(Module.of('puppetlabs/java', "include java"))
    }
}
