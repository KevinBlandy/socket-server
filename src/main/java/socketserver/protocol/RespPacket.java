package socketserver.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import lombok.Builder;
import lombok.With;
import socketserver.utils.CRC16;

/**
 * 
 * 服务器响应给客户端的 Packet
 * 
 * @author KevinBlandy
 *
 */
@With
@Builder
public class RespPacket extends Packet {
	
	public static final Integer HEADER = 0xDCFE;

	@Override
	public ByteBuf encode() {
		ByteBuf buf = Unpooled.buffer();
		
		buf.writeShort(super.getHeader());
		buf.writeShort(super.getCommand().value);
		buf.writeBytes(ByteBufUtil.decodeHexDump(super.getImei()));
		buf.writeShort(super.getContent() == null ? 0 : super.getContent().length);
		
		if (super.getContent() != null) {
			buf.writeBytes(super.getContent());
		}
		buf.writeShort(CRC16.crc16(ByteBufUtil.getBytes(buf)));
		
		return buf.readerIndex(0);
	}

	@Override
	public void decode(ByteBuf byteBuf) {
		
	}
}
