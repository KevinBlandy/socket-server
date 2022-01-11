package socketserver.protocol;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * 指令
 * 
 * @author KevinBlandy
 *
 */
public enum Command {
	
	/**
	 * 设备上传
	 */
	REGISTER(0x1001, "注册", "设备连接注册到服务器", null),
	STATUS(0x1002, "上传状态包", "设备定时发送状态数据到服务器", null),
	DATA(0x1003, "上传数据包", "设备定时发送数据包到服务器", null),
	
	
	
	;
	
	public final Integer value;
	public final String name;
	public final String description;
	public final BiConsumer<ChannelHandlerContext, ReqPacket> handler;
	
	public static final Map<Integer, Command> VALUES = new HashMap<>();
	
	static {
		for (Command command : Command.values()) {
			if (VALUES.containsKey(command.value)) {
				throw new IllegalArgumentException("重复的指令值定义: " + Integer.toHexString(command.value));
			}
			VALUES.put(command.value, command);
		}
	}
	
	
	private Command(Integer value, String name, String description, BiConsumer<ChannelHandlerContext, ReqPacket> handler) {
		this.value = value;
		this.name = name;
		this.description = description;
		this.handler = handler;
	}
}
