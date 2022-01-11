package socketserver.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import socketserver.protocol.RespPacket;

public class RespPacketEncoder extends MessageToByteEncoder<RespPacket> {

	@Override
	protected void encode(ChannelHandlerContext ctx, RespPacket msg, ByteBuf out) throws Exception {
		out.writeBytes(msg.encode());
	}
}
