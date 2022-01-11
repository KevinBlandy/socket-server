package socketserver.handler;


import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import socketserver.group.ChannelGroups;
import socketserver.group.DeviceInfo;
import socketserver.protocol.Command;
import socketserver.protocol.ReqPacket;

@Slf4j
public class RegisterHandler extends SimpleChannelInboundHandler<ReqPacket> {
	
	public static final RegisterHandler INSTANCE = new RegisterHandler();

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ReqPacket msg) throws Exception {
		if (msg.getCommand() != Command.REGISTER) {
			/**
			 * 第一个数据包不是注册，视为非法连接，直接关闭
			 */
			ctx.close();
			return ;
		}
		
		DeviceInfo deviceInfo = (DeviceInfo) Command.REGISTER.handler.apply(ctx, msg);
		
		if (deviceInfo == null) {
			ctx.channel().close();
			return ;
		}
		
		ctx.channel().closeFuture().addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				
				Channel channel = future.channel();
				
				DeviceInfo deviceInfo = ChannelGroups.CHANNELS.remove(channel);
				if (deviceInfo != null) {
					/**
					 * 设备就算断开了连接，也不移除此设备（已经经过认证）的连接信息，方便查询到设备离线时间信息等等。
					 * 但是会浪费内存，需要取舍
					 */
					// ChannelGroups.DEVICES.remove(deviceInfo.getCode());	
					
					deviceInfo.setChannel(null);							 // 置空channel 防止内存泄露
					deviceInfo.setDisConnectAt(System.currentTimeMillis()); 	// 断开连接的时间
				}
				
				log.info("连接断开: {}, 移除在线设备信息: {}", channel.id(), deviceInfo.getCode());				
			}
		});

		ChannelGroups.CHANNELS.put(ctx.channel(), deviceInfo);
		
		DeviceInfo oldDeviceInfo = ChannelGroups.DEVICES.put(deviceInfo.getCode(), deviceInfo);
		
		log.info("添加到在线设备，在线设备数量: {}", ChannelGroups.CHANNELS.size());
		
		// 关闭这个设备旧的连接
		if (oldDeviceInfo != null && oldDeviceInfo.getChannel() != null) {
			log.info("设备重复连接: {}, 关闭旧连接: {}", deviceInfo.getCode(), oldDeviceInfo.getChannel().id());
			oldDeviceInfo.getChannel().close();
		}
		
		// 验证完成后，移除登录验证器
		ctx.channel().pipeline().remove(this);
	}
}
