package com.mrpc.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Rpc请求
 */
@Data
public class RpcRequest implements Serializable {
    // 服务名
    public String serverName;
    // 方法名
    public String methodName;
    // 参数类型
    public Class<?>[] parameterTypes;
    // 参数
    public Object[] parameters;
}
