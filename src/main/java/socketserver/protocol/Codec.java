package socketserver.protocol;


public interface Codec {
	
	byte[] encode();
	
	void decode(byte[] bytes);
}
