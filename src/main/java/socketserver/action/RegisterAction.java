package socketserver.action;

import java.util.function.BiFunction;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import socketserver.group.DeviceInfo;
import socketserver.protocol.RegisterPacket;
import socketserver.protocol.ReqPacket;

@Slf4j
public class RegisterAction implements BiFunction<ChannelHandlerContext, ReqPacket, DeviceInfo>{

	@Override
	public DeviceInfo apply(ChannelHandlerContext t, ReqPacket u) {
		
		RegisterPacket registerPacket = new RegisterPacket();
		registerPacket.decode(Unpooled.wrappedBuffer(u.getContent()));
		
		log.info("设备注册: {}", registerPacket);
		
		// TODO 校验，校验失败返回错误信息，关闭连接，返回 null
		
		return DeviceInfo.builder()
				.code(u.getImei())
				.connectAt(System.currentTimeMillis())
				.channel(t.channel())
				.build();
	}
}
