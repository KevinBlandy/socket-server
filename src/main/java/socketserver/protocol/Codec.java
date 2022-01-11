package socketserver.protocol;

import io.netty.buffer.ByteBuf;

public interface Codec {
	/**
	 * 编码为字节
	 * @return
	 */
	ByteBuf encode ();
	
	/**
	 * 解码数据，填充对象
	 * @param bytes
	 */
	void decode(ByteBuf byteBuf);
}
