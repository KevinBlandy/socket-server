package socketserver.action;

import java.util.function.BiFunction;

import io.netty.channel.ChannelHandlerContext;
import socketserver.protocol.ReqPacket;

/**
 *
 * 数据包
 * @author KevinBlandy
 *
 */
public class DataAction implements BiFunction<ChannelHandlerContext, ReqPacket, Object>{

	@Override
	public Object apply(ChannelHandlerContext t, ReqPacket u) {
		
		
		
		return null;
	}

}
