package com.mrpc;

import com.mrpc.server.HttpServer;
import com.mrpc.server.VertxHttpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MRpcApplication {

    public static void main(String[] args) {
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }

}
