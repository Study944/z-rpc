package com.mrpc.utils;

import cn.hutool.setting.dialect.Props;

/**
 * 配置文件加载工具类
 */
public class ConfigUtil {

    public static <T> T loadConfig(Class<T> clazz, String prefix){
        return loadConfig(clazz, prefix, "");
    }

    public static <T> T loadConfig(Class<T> clazz, String prefix, String environment){
        // 1.获取配置文件的名称
        environment = environment==""? "" : "-" + environment;
        String fileName = "application" + environment + ".properties";
        // 2.加载配置文件
        Props props = new Props(fileName);
        return props.toBean(clazz, prefix);
    }

}
