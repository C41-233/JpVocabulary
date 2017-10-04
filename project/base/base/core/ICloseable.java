package base.core;

import java.io.Closeable;

public interface ICloseable extends Closeable, AutoCloseable{

	public void close();
	
}
