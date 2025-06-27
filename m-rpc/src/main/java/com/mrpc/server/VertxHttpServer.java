package com.mrpc.server;

import io.vertx.core.Vertx;

public class VertxHttpServer implements HttpServer{
    @Override
    public void doStart(int port) {
        // 1. 创建vertx
        Vertx vertx = Vertx.vertx();
        // 2. 创建httpServer
        io.vertx.core.http.HttpServer httpServer = vertx.createHttpServer();
        // 3. 监听端口处理请求
        httpServer.requestHandler(new HttpServerHandler());
        /**httpServer.requestHandler(request -> {
            // 打印请求信息
            System.out.println("收到请求："+ request.path()+ " "+ request.method()+ " "+ request.uri());
            // 响应请求
            request.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello Vertx");
        });**/
        // 4. 启动服务
        httpServer.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("正在监听端口 " + port);
            } else {
                System.err.println("监听失败 " + result.cause());
            }
        });
    }
}
