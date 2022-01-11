package socketserver.handler;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import socketserver.protocol.ReqPacket;
import socketserver.utils.CRC16;


@Slf4j
public class ReqPacketDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		
		ReqPacket packet = new ReqPacket();
		packet.decode(in);

		// CRC 校验
		int checkSum = CRC16.crc16(in.readerIndex(0).writerIndex(in.readableBytes() - 1));
		
		if (checkSum != packet.getCheckSum().intValue()) {
			log.warn("CRC16校验失败: remote={}, local={}", Integer.toHexString(packet.getCheckSum()), Integer.toHexString(checkSum));
			ctx.close();
			return ;
		}
		
		out.add(packet);
	}
}
