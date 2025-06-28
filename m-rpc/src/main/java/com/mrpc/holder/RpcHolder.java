package com.mrpc.holder;

import com.mrpc.config.RpcConfig;
import com.mrpc.utils.ConfigUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcHolder {

    private static volatile RpcConfig rpcConfig;

    /**
     * 初始化--有配置文件
     * @param newRpcConfig
     */
    public static void init(RpcConfig newRpcConfig){
        rpcConfig = newRpcConfig;
        log.info("RpcConfig init:{}",rpcConfig);
    }

    /**
     * 初始化--无配置文件
     */
    public static void init(){
        RpcConfig newRpcConfig = ConfigUtil.loadConfig(RpcConfig.class, "rpc");
        init(newRpcConfig);
    }

    /**
     * 获取RpcConfig
     */
    public static RpcConfig getRpcConfig(){
        if (rpcConfig == null){
            synchronized (RpcHolder.class){
                init();
            }
        }
        return rpcConfig;
    }

}
