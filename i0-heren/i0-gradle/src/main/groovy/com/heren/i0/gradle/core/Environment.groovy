package com.heren.core.gradle.core

import org.gradle.api.Named

class Environment implements Named {
    String name
    Hosting hosting

    Environment(String name) {
        this.name = name
    }
}
