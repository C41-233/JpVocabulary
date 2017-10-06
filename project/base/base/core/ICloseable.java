package base.core;

import java.io.Closeable;

public interface ICloseable extends Closeable, AutoCloseable{

	@Override
	public void close();
	
}
