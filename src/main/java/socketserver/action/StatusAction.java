package socketserver.action;

import java.util.function.BiFunction;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import socketserver.protocol.ReqPacket;
import socketserver.protocol.StatusPacket;

/**
 *
 * 状态数据包
 * @author KevinBlandy
 *
 */
@Slf4j
public class StatusAction implements BiFunction<ChannelHandlerContext, ReqPacket, Object>{

	@Override
	public Object apply(ChannelHandlerContext t, ReqPacket u) {
		
		StatusPacket statusPacket = new StatusPacket();
		u.decode(Unpooled.wrappedBuffer(u.getContent()));
		
		log.info("设备状态上传: {}", statusPacket);
		
		return null;
	}

}
