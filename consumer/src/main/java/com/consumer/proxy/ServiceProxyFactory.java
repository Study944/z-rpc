package com.consumer.proxy;

import java.lang.reflect.Proxy;

/**
 * 代理工厂类--用于简化创建代理对象的过程
 */
public class ServiceProxyFactory {

    public static <T> T getProxy(Class<T> interfaceClass) {
        return (T)Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class[]{interfaceClass},
                new ServiceProxy());
    }

}
