package com.mrpc.config;

import lombok.Data;

/**
 * 全局配置类
 */
@Data
public class RpcConfig {

    // 服务名称
    private String name = "m-rpc";
    // 服务版本
    private String version = "1.0";
    // 服务地址
    private String serverHost = "127.0.0.1";
    // 服务端口
    private int serverPort = 8080;

}
