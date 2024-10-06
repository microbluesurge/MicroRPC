package com.apple;


import com.banana.annotations.RpcCall;
import com.test.api.TestService;
import org.springframework.stereotype.Controller;

//一定要确保包含RPC调用的类被注册为Bean
@Controller()
public class TestController {


    //对于RPC接口加个这个声明就行了
    @RpcCall
    private TestService testService;



    public void test() {
        testService.hello("hhhh");
    }
}
