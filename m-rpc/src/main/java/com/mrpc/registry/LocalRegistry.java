package com.mrpc.registry;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地注册中心
 */
public class LocalRegistry {
    // key--服务名，value--接口实现类
    static ConcurrentHashMap<String, Class<?>> registry = new ConcurrentHashMap<>();

    /**
     * 服务注册
     * @param serverName
     * @param implClass
     */
    public static void register(String serverName, Class<?> implClass) {
        System.out.println("服务注册："+ serverName+ " "+ implClass.getName());
        registry.put(serverName, implClass);
    }

    /**
     * 服务发现
     * @param serverName
     */
    public static Class<?> get(String serverName) {
        return registry.get(serverName);
    }

    /**
     * 服务注销
     * @param serverName
     */
    public static void remove(String serverName){
        registry.remove(serverName);
    }

}
