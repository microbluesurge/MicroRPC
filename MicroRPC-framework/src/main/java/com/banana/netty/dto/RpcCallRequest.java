package com.banana.netty.dto;

import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;


@ToString
@Builder
@Data
public class RpcCallRequest {

    String interfaceName;

    String methodName;

    Class<?>[] paramTypes ;

    Object[] params;

}
