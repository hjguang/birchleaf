package netty;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

class TcpServerHandler extends ChannelInboundHandlerAdapter {

    private Object obj;
    private Object response;

    public TcpServerHandler(Object obj) {
        super();
        this.obj = obj;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MethodAndArgs methodAndArgs = (MethodAndArgs) msg;
        Method method = obj.getClass().getMethod(methodAndArgs.getMethodName(), methodAndArgs.getTypes());
        ctx.writeAndFlush(method.invoke(obj, methodAndArgs.getObjects()));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client die");
    };
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

/**
 * 鐎广垺鍩涚粩顖氼槱閻烇拷
 * 
 * @author hadoop
 *
 */
class TcpClientHander extends ChannelInboundHandlerAdapter {
    private Object response;

    public Object getResponse() {
        return response;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        response = msg;
        System.out.println("client閹恒儲鏁归崚鐗堟箛閸斺�虫珤鏉╂柨娲栭惃鍕Х閹拷:" + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
    }
}

public class RpcFramework {

    /**
     * 閺堝秴濮熷▔銊ュ斀
     * 
     * @param obj
     *            闂囷拷鐟曚焦鏁為崘宀�娈戦張宥呭鐎电钖�
     * @param port
     *            缁旑垰褰�
     * @param ip
     *            閸︽澘娼�
     * @throws InterruptedException
     */
    public static void regist(final Object obj, int port, String ip) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        if (obj == null)
            throw new IllegalArgumentException("鐎电钖勬稉宥堝厴娑撶皠ull");
        if (port <= 0 || port > 65535)
            throw new IllegalArgumentException("闁挎瑨顕ら惃鍕伂閸欙拷" + port);
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<Channel>() {

            @Override
            protected void initChannel(Channel ch) throws Exception {
                // TODO Auto-generated method stub
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                pipeline.addLast(new LengthFieldPrepender(4));
                pipeline.addLast("encoder", new ObjectEncoder());
                pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                // pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                // pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                pipeline.addLast(new TcpServerHandler(obj));
            }
        });
        ChannelFuture f = bootstrap.bind(ip, port).sync();
        f.channel().closeFuture().sync();
    }

    @SuppressWarnings("unchecked")
    public static <T> T getObj(Class<T> interfaceClass, final String host, final int port) {
        if (interfaceClass == null)
            throw new IllegalArgumentException("閹恒儱褰涚猾璇茬�锋稉宥堝厴娑撹櫣鈹�");
        if (!interfaceClass.isInterface())
            throw new IllegalArgumentException("缁鎮�" + interfaceClass.getName() + "韫囧懘銆忛弰顖涘复閸欙拷");
        if (host == null || host.length() == 0)
            throw new IllegalArgumentException("閻╊喗鐖ｆ稉缁樻簚娑撳秷鍏樻稉铏光敄");
        if (port <= 0 || port > 65535)
            throw new IllegalArgumentException("缁旑垰褰涢柨娆掝嚖閿涳拷" + port);

        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] { interfaceClass },
                new InvocationHandler() {

                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        MethodAndArgs mArgs = new MethodAndArgs(method.getName(), method.getParameterTypes(), args);
                        final TcpClientHander tcpClientHander = new TcpClientHander();
                        EventLoopGroup group = new NioEventLoopGroup();
                        try {
                            Bootstrap b = new Bootstrap();
                            b.group(group);
                            // b.channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY,
                            // true);
                            b.channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true);

                            b.handler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    ChannelPipeline pipeline = ch.pipeline();
                                    pipeline.addLast("frameDecoder",
                                            new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                                    pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                                    pipeline.addLast("encoder", new ObjectEncoder());
                                    pipeline.addLast("decoder",
                                            new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                                    pipeline.addLast("handler", tcpClientHander);
                                }
                            });

                            ChannelFuture f = b.connect(host, port).sync();

                            f.channel().writeAndFlush(mArgs).sync();
                            f.channel().closeFuture().sync();

                        } catch (Exception e) {

                        } finally {

                            group.shutdownGracefully();
                        }
                        return tcpClientHander.getResponse();
                    }
                });
    }
}