package com.consumer;

import com.common.model.User;
import com.common.service.UserService;
import com.consumer.proxy.ServiceProxyFactory;
import com.mrpc.config.RpcConfig;
import com.mrpc.holder.RpcHolder;
import com.mrpc.utils.ConfigUtil;

public class ConsumerApplicationTest {

    public static void main(String[] args) {
        RpcConfig rpcConfig = RpcHolder.getRpcConfig();
        System.out.println(rpcConfig);
    }

}
