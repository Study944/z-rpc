package com.provider;

import com.common.service.UserService;
import com.mrpc.registry.LocalRegistry;
import com.mrpc.server.HttpServer;
import com.mrpc.server.VertxHttpServer;
import com.provider.impl.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class ProviderApplication {

    public static void main(String[] args) {
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }

}
