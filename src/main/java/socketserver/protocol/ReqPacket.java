package socketserver.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import socketserver.utils.CRC16;

/**
 * 
 * 设备请求服务器 Packet
 * 
 * @author KevinBlandy
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ReqPacket extends Packet {
	
	public static final Integer HEADER = 0xFEDC;

	private Integer din; // 2字节，鉴权码

	@Override
	public ByteBuf encode() {
		ByteBuf buf = Unpooled.buffer();
		buf.writeShort(super.getHeader());
		buf.writeShort(super.getCommand().value);
		buf.writeBytes(ByteBufUtil.decodeHexDump(super.getImei()));
		buf.writeShort(this.din);
		buf.writeShort(super.getContent() == null ? 0 : super.getContent().length);
		if (super.getContent() != null) {
			buf.writeBytes(super.getContent());
		}
		buf.writeShort(CRC16.crc16(ByteBufUtil.getBytes(buf)));
		return buf.readerIndex(0);
	}

	@Override
	public void decode(ByteBuf byteBuf) {
		super.setHeader(byteBuf.readUnsignedShort());
		super.setCommand(Command.VALUES.get(byteBuf.readUnsignedShort()));
		super.setImei(ByteBufUtil.hexDump(byteBuf.readBytes(6)));
		
		this.din = byteBuf.readUnsignedShort();
		
		byte[] content = new byte[byteBuf.readUnsignedShort()];
		if (content.length > 0) {
			byteBuf.readBytes(content);
			super.setContent(content);
		}
		super.setCheckSum(byteBuf.readUnsignedShort());
	}
}
