package com.consumer.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.common.model.User;
import com.common.service.UserService;
import com.mrpc.model.RpcRequest;
import com.mrpc.model.RpcResponse;
import com.mrpc.serializer.JdkSerializer;
import com.mrpc.serializer.Serializer;

import java.io.IOException;

/**
 * 静态代理--为每一个接口编写一个代理类，负责发送请求
 */
public class UserServiceStaticProxy implements UserService {
    @Override
    public User getUser(User user) {
        // 1.编写Rpc请求
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setServerName(UserService.class.getName());
        System.out.println(UserService.class.getName());
        rpcRequest.setMethodName("getUser");
        rpcRequest.setParameterTypes(new Class[]{User.class});
        rpcRequest.setParameters(new Object[]{user});
        // 2.序列化请求
        Serializer serializer = new JdkSerializer();
        try {
            byte[] bytes = serializer.serialize(rpcRequest);
            // 3.发送请求
            try (HttpResponse response = HttpRequest.post("http://localhost:8080").body(bytes).execute();){
                // 4.反序列化响应
                RpcResponse rpcResponse = serializer.deserialize(response.bodyBytes(), RpcResponse.class);
                User result = (User) rpcResponse.getData();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
