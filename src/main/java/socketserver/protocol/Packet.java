package socketserver.protocol;

import io.netty.buffer.ByteBuf;
import lombok.Data;

@Data
public abstract class Packet {
	
	private Integer header;			// 2字节，帧头
	private Command command;		// 2字节，指令
	private String imei;			// 6字节，IMEI，设备ID，HEX
	// private Integer contentLength;	// 2字节，内容长度
	private byte[] content;			// N字节，内容
	private Integer checkSum; 		// 2字节，CRC16校验码
	
	/**
	 * 编码为字节
	 * @return
	 */
	abstract public ByteBuf encode ();
	
	/**
	 * 解码数据，填充对象
	 * @param bytes
	 */
	abstract public void decode(ByteBuf byteBuf);
}
