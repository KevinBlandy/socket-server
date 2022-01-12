package socketserver.protocol;

import io.netty.buffer.ByteBuf;
import lombok.Data;

@Data
public class StatusPacket implements Codec{

	private Long signalStrength;		//4字节，信号强度，补码
	private Integer voltage;		// 2字节，电压，三位小数
	private Short errorCode;		// 1字节，错误码
	
	@Override
	public ByteBuf encode() {
		return null;
	}

	@Override
	public void decode(ByteBuf byteBuf) {
		this.signalStrength= byteBuf.readUnsignedInt();
		this.voltage = byteBuf.readUnsignedShort();
		this.errorCode = byteBuf.readUnsignedByte();
	}

}
