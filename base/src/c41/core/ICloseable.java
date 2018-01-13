package c41.core;

import java.io.Closeable;

public interface ICloseable extends Closeable{

	@Override
	public void close();
	
}
