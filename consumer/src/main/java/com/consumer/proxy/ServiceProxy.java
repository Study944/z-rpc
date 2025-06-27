package com.consumer.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.mrpc.model.RpcRequest;
import com.mrpc.model.RpcResponse;
import com.mrpc.serializer.JdkSerializer;
import com.mrpc.serializer.Serializer;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理--通用创建代理方法
 */
public class ServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 1.编写Rpc请求
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setServerName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParameterTypes(method.getParameterTypes());
        rpcRequest.setParameters(args);
        // 2.序列化请求
        Serializer serializer = new JdkSerializer();
        try {
            byte[] bytes = serializer.serialize(rpcRequest);
            // 3.发送请求
            try (HttpResponse response = HttpRequest.post("http://localhost:8080").body(bytes).execute();){
                // 4.反序列化响应
                RpcResponse rpcResponse = serializer.deserialize(response.bodyBytes(), RpcResponse.class);
                Object result = rpcResponse.getData();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
