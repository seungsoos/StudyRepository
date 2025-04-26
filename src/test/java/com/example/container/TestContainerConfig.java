package com.example.container;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

//@Testcontainers
public class TestContainerConfig {

//    @Container
//    @ServiceConnection(name = "redis")
    public static final GenericContainer<?> REDIS_CONTAINER = new GenericContainer<>("redis:7.2.3")
            .withExposedPorts(6379);
}
