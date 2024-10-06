package com.banana.netty.dto;

import lombok.*;

import java.util.Deque;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@ToString
@Builder
@Data
public class RpcCallRequest {

    String interfaceName;

    String methodName;

    Class<?>[] paramTypes ;

    Object[] params;

}
