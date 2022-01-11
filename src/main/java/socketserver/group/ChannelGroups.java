package socketserver.group;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import io.netty.channel.Channel;


public class ChannelGroups {
	
	/**
	 * 连接 & 设备信息
	 */
	public final static ConcurrentMap<Channel, DeviceInfo> CHANNELS = new ConcurrentHashMap<>();

	/**
	 * 设备ID & 设备信息
	 */
	public final static ConcurrentMap<String, DeviceInfo> DEVICES = new ConcurrentHashMap<>();
	
}
