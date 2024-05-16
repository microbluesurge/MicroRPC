package com.banana.netty.server;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RpcNettyServerHandler extends ChannelInboundHandlerAdapter {
    public RpcNettyServerHandler() {


    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            if (msg instanceof String) {
                log.info("server receive msg: [{}] ", msg);
                ctx.writeAndFlush("the msg is : " + msg).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        } finally {
            //Ensure that ByteBuf is released, otherwise there may be memory leaks
            ReferenceCountUtil.release(msg);
        }
    }
}
