package com.banana.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.banana")
public class RpcClientMain {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(RpcClientMain.class);
        TestController testController = (TestController) applicationContext.getBean("testController");
        testController.test();
    }
}
