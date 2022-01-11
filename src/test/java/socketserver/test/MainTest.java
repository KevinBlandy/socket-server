package socketserver.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainTest {
	public static void main(String[] args) {
		ByteBuf in = Unpooled.wrappedBuffer(new byte[] {0x01, 0x02, 0x03, 0x04});
		
		in.readByte();

		ByteBuf newBuf = in.readBytes(2);
		
		// log.info("arr={}", ByteBufUtil.getBytes(in));
		
		log.info("buf = {}", in);
		log.info("new buf={}", newBuf);
		
		
	}
}
