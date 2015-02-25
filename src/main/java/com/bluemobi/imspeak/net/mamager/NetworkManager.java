package com.bluemobi.imspeak.net.mamager;


/**
 * Created by wangbin on 2015/1/22.
 */

import com.bluemobi.imspeak.net.configure.Router;
import com.bluemobi.imspeak.net.configure.SysConstants;
import com.bluemobi.imspeak.net.extend.ActionHolder;
import com.bluemobi.imspeak.net.extend.dispatch.DefaultRequestDispatcher;
import com.bluemobi.imspeak.net.lib.logger.Logger;
import com.bluemobi.imspeak.net.lib.logger.LoggerFactory;
import com.bluemobi.imspeak.net.lib.net.BinaryMessageHandler;
import com.bluemobi.imspeak.net.lib.net.FrameBinaryDecoder;
import com.bluemobi.imspeak.net.lib.net.FrameBinaryEncoder;
import com.bluemobi.imspeak.net.lib.net.IDispatcher;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 网络管理
 */
public class NetworkManager {

    private static final Logger logger = LoggerFactory.getLogger(NetworkManager.class);

    private static NetworkManager manager = new NetworkManager();

    private int port = SysConstants.SERVER_DEFAULT_PORT; // 服务器端口

    private ServerBootstrap bootstrap; // server

    private ChannelGroup channelGroup; // 打开的所有channel的群组，用户关闭


    private ExecutorService nettyBossThreadPool; // netty boss线程池

    private ExecutorService nettyWorkerTheadPool; // netty worker线程池

    // 所有连接着的客户端的列表
    private ConcurrentHashMap<String, ChannelHandlerContext> clientMap = new ConcurrentHashMap<String, ChannelHandlerContext>();


    private NetworkManager(){};

    public static NetworkManager getInstance(){
        return manager;
    }

    public void init(int port) throws Exception{
        //消息分发器
        IDispatcher dispatcher = initDispatcher();
        //消息接收器
        final SimpleChannelHandler channelHandler = new BinaryMessageHandler(dispatcher);

        final ExecutionHandler executionHandler = new ExecutionHandler(
                new OrderedMemoryAwareThreadPoolExecutor(16, 1048576, 1048576));

        this.port = port;

        nettyBossThreadPool = Executors.newCachedThreadPool(); // boss线程池，用于接收链接
        nettyWorkerTheadPool = Executors.newCachedThreadPool(); // worker线程池，boss线程接收请求之后由worker线程处理请求

        ChannelFactory factory = new NioServerSocketChannelFactory(
                nettyBossThreadPool, nettyWorkerTheadPool);
        bootstrap = new ServerBootstrap(factory);

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                // 接收的数据包解码
                pipeline.addLast("decoder", new FrameBinaryDecoder());
                // 发送的数据包编码
                pipeline.addLast("encoder", new FrameBinaryEncoder());
                pipeline.addLast("execution", executionHandler);
                // 具体的业务处理，这个handler只负责接收数据，并传递给dispatcher
                pipeline.addLast("handler", channelHandler);

                return pipeline;
            }

        });


        bootstrap.setOption("child.tcpNoDelay", true); // 无延迟发生
        bootstrap.setOption("child.keepAlive", true); // tcp长连

        Channel serverChannel = bootstrap.bind(new InetSocketAddress(this.port)); // 打开网络端口

        channelGroup = new DefaultChannelGroup(); // 添加到group
        channelGroup.add(serverChannel);

        logger.info("server started on port " + this.port);


    }


    public IDispatcher initDispatcher() throws Exception{
        ActionHolder actionHolder = new ActionHolder();
        Router router = ConfigureManager.getInstance().getActionRouter();
        actionHolder.put(router);
        IDispatcher dispatcher = new DefaultRequestDispatcher(actionHolder);
        return dispatcher;
    }


    public void addClient(ChannelHandlerContext context){
        if(context==null){
            return;
        }
        String name = context.getChannel().getRemoteAddress().toString();
        clientMap.putIfAbsent(name, context);
    }


    /**
     *
     * @Description: 删除一个链接的客户端
     * @param context
     */
    public void removeClient(ChannelHandlerContext context) {
        if (context == null) {
            return;
        }
        String name = context.getChannel().getRemoteAddress().toString();
        clientMap.remove(name);
    }
}
