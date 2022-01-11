package socketserver.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPacket implements Codec {
	
	private String version; // 4个字节，版本号
	private Integer type;	// 2个字节，设备类型
	private Integer templates;	// 2个字节，模板数量
	private Integer templateId;	// 2个字节，自动匹配对应模板
	
	@Override
	public ByteBuf encode() {
		// ByteBufAllocator.DEFAULT.ioBuffer();
	 	ByteBuf buf = Unpooled.buffer();
	 	buf.writeBytes(ByteBufUtil.decodeHexDump(this.version));
	 	buf.writeShort(this.type);
	 	buf.writeShort(this.templates);
	 	buf.writeShort(this.templateId);
		return buf;
	}

	@Override
	public void decode(ByteBuf byteBuf) {
		this.version = ByteBufUtil.hexDump(byteBuf.readBytes(6));
		this.type = byteBuf.readUnsignedShort();
		this.templates = byteBuf.readUnsignedShort();
		this.templateId = byteBuf.readUnsignedShort();
	}
}
