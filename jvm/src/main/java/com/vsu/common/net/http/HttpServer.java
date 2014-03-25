package com.vsu.common.net.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Adapted from https://github.com/netty/netty/tree/netty-4.0.10.Final/example/src/main/java/io/netty/example/http/websocketx/server
 *
 * @author Stephen Mallette (http://stephen.genoprime.com)
 * @author Victor Su
 */
public class HttpServer extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);
    private HttpSettings settings;
    private final ChannelInitializer<SocketChannel> initializer;
    private Channel ch;

    public HttpServer(final HttpSettings settings, ChannelInitializer<SocketChannel> initializer) {
        this.settings = settings;
        this.initializer = initializer;
    }

    @Override
    public void run() {
        final EventLoopGroup bossGroup = new NioEventLoopGroup(settings.threadPoolBoss);
        final EventLoopGroup workerGroup = new NioEventLoopGroup(settings.threadPoolWorker);
        try {
            final ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(initializer);

            ch = b.bind(settings.host, settings.port).sync().channel();

            logger.info("HTTP server configured with worker thread pool of {} and boss thread pool of {}",
                    settings.threadPoolWorker, settings.threadPoolBoss);
            logger.info("Listening on address {} and port {}.", settings.host, settings.port);

            ch.closeFuture().sync();

        } catch (InterruptedException e) {

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void shutdown() {
        ch.close();
    }

}
