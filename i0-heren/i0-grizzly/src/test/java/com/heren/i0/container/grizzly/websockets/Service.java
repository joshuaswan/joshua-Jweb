package com.heren.core.container.grizzly.websockets;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

public class Service {

    public String message() {
        return "injected";
    }
}