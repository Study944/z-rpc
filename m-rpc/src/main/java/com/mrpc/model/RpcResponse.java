package com.mrpc.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Rpc响应
 */
@Data
public class RpcResponse implements Serializable {

    // 响应数据
    public Object data;
    // 响应数据类型
    public Class<?> dataType;
    // 响应信息
    public String message;
    // 响应异常
    public Exception exception;

}
