package com.consumer;

import com.common.model.User;
import com.common.service.UserService;
import com.consumer.proxy.ServiceProxyFactory;

public class ConsumerApplication {

    public static void main(String[] args) {
        // UserService = new UserServiceStaticProxy();
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("远程过程调用");
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println("消费者成功调用");
        }else {
            System.out.println("消费者调用失败");
        }

    }

}
