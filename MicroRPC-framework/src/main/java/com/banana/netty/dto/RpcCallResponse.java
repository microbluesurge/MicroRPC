package com.banana.netty.dto;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Builder
@Data
public class RpcCallResponse {

    Integer status;

    String info;

    Object data;

    public static RpcCallResponse newSuccessResponse(Object result) {
        return RpcCallResponse.builder()
                .data(result)
                .status(200)
                .info("rpc call success")
                .build();
    }



}
