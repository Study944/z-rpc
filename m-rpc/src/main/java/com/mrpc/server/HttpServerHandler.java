package com.mrpc.server;

import com.mrpc.model.RpcRequest;
import com.mrpc.model.RpcResponse;
import com.mrpc.registry.LocalRegistry;
import com.mrpc.serializer.JdkSerializer;
import com.mrpc.serializer.Serializer;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 请求处理器
 */
public class HttpServerHandler implements Handler<HttpServerRequest> {
    /**
     * 处理请求
     * @param httpServerRequest
     */
    @Override
    public void handle(HttpServerRequest httpServerRequest) {
        System.out.println("收到请求："+ httpServerRequest.path()+ " "+ httpServerRequest.method()+ " "+ httpServerRequest.uri());
        // 1.反序列化为RpcRequest
        Serializer serializer = new JdkSerializer();
        httpServerRequest.bodyHandler(body -> {
            byte[] bytes = body.getBytes();
            RpcRequest rpcRequest = null;
            try {
                rpcRequest = serializer.deserialize(bytes, RpcRequest.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // 创建返回结果
            RpcResponse rpcResponse = new RpcResponse();
            if (rpcRequest== null){
                rpcResponse.setMessage("请求为空");
                doResponse(httpServerRequest,rpcResponse,serializer);
                return;
            }
            // 2.查询注册表，获取方法实现类
            Class<?> aClass = LocalRegistry.get(rpcRequest.getServerName());
            try {
                // 通过反射调用方法
                Method method = aClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object result = method.invoke(aClass.newInstance(), rpcRequest.getParameters());
                // 设置返回结果
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("请求调用成功");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            doResponse(httpServerRequest,rpcResponse,serializer);
        });
    }

    /**
     * 序列化响应结果，并返回给客户端
     * @param httpServerRequest
     * @param rpcResponse
     * @param serializer
     */
    public void doResponse(HttpServerRequest httpServerRequest,RpcResponse rpcResponse,Serializer serializer){
        HttpServerResponse httpServerResponse = httpServerRequest
                .response()
                .putHeader("content-type", "application/json");
        try {
            byte[] bytes = serializer.serialize(rpcResponse);
            // Buffer是Vertx的高性能数据容器，保留原始字节原样传输
            httpServerResponse.end(Buffer.buffer(bytes));
        } catch (IOException e) {
            e.printStackTrace();
            rpcResponse.setMessage("请求调用失败");
            try {
                httpServerResponse.end(Buffer.buffer(serializer.serialize(rpcResponse)));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
