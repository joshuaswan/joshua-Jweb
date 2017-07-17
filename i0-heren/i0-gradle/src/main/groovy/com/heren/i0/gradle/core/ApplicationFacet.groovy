package com.heren.core.gradle.core

import org.gradle.api.Project

interface ApplicationFacet extends Facet{
    void generateScaffold(Project project)

    void configure(Project project)
}
