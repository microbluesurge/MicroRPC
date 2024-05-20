package com.banana.example;


import com.banana.annotations.RpcCall;
import com.test.api.TestService;
import org.springframework.stereotype.Component;

@Component
public class TestController {


    @RpcCall
    private TestService testService;

    public void test() {
        testService.hello("hhhh");
    }
}
