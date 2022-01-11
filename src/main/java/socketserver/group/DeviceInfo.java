package socketserver.group;

import java.io.Serializable;

import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;


@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2247166241941800730L;
	
	// 设备编号，必须全局唯一
	private String code;

	// 连接通道
	private Channel channel;
	
	// 最后一次连接时间
	private long connectAt;
	
	// 最后一次断开连接时间戳
	private long disConnectAt;
	
	// 最后发送时间
	private long lastSendAt;
	
	// 最后接收消息时间
	private long lastRevcAt;
	
	// 发送字节数量
	private long sendBytes;
	
	// 接收字节数量
	private long recvBytes;
	
	/**
	 * 设备是否在线
	 * @return
	 */
	public boolean online () {
		return this.channel != null && this.channel.isActive();
	}
}
