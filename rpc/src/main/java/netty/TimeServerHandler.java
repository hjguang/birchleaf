package netty;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    private int counter;
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req,"UTF-8").substring(0,req.length-System.getProperty("line.separator").length());
        System.out.println("the time server recive: " + body +" ; the counter is "  + ++counter);
        String curentTime = "query time order".equalsIgnoreCase(body)? new Date(System.currentTimeMillis()).toString():"bad order";
        curentTime = curentTime + System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(curentTime.getBytes());
        ctx.writeAndFlush(resp);
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
        ctx.close();
    }
}
