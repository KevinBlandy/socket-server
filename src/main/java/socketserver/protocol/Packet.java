package socketserver.protocol;

import lombok.Data;

@Data
public abstract class Packet implements Codec {
	
	private Integer header;			// 2字节，帧头
	private Command command;		// 2字节，指令
	private String imei;			// 6字节，IMEI，设备ID，HEX
	// private Integer contentLength;	// 2字节，内容长度
	private byte[] content;			// N字节，内容
	private Integer checkSum; 		// 2字节，CRC16校验码
}
