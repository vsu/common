package com.vsu.common.net.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author Victor Su
 */
public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static void sendHttpResponse(final ChannelHandlerContext ctx,
                                        final HttpResponseStatus status) {

        final FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status);

        sendHttpResponse(ctx, response);
    }

    public static void sendHttpResponse(final ChannelHandlerContext ctx,
                                        final HttpResponseStatus status,
                                        final String content) {

        sendHttpResponse(ctx, status, content, "text/plain; charset=UTF-8");
    }

    public static void sendHttpResponse(final ChannelHandlerContext ctx,
                                        final HttpResponseStatus status,
                                        final String content,
                                        final String contentType) {

        final ByteBuf buf = Unpooled.copiedBuffer(content.toString(), CharsetUtil.UTF_8);
        final FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status, buf);
        response.headers().set(CONTENT_TYPE, contentType);

        sendHttpResponse(ctx, response);
    }

    public static void sendHttpResponse(final ChannelHandlerContext ctx,
                                        final FullHttpResponse res) {
        // Generate an error page if response getStatus code is not OK (200).
        if (res.getStatus().code() != 200) {
            final ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            setContentLength(res, res.content().readableBytes());
        }

        // Close the connection as soon as the response is sent.
        ctx.writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
    }
}
