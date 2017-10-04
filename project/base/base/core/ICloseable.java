package base.core;

import com.sun.xml.internal.ws.Closeable;

public interface ICloseable extends Closeable, AutoCloseable{

	public void close();
	
}
