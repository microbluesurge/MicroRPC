package com.apple;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;


//banana包是RPC框架实现，需要扫到，apple就是测试程序所在的包
@ComponentScan(basePackages = {"com.banana", "com.apple"})
public class RpcClientMain {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(RpcClientMain.class);
        TestController testController = (TestController) applicationContext.getBean("testController");
        testController.test();
    }
}
