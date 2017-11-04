package base.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import base.core.ICloseable;

public class FileWriter implements ICloseable{

	private final OutputStreamWriter os;
	
	public FileWriter(File file, String charset) {
		try {
			this.os = new OutputStreamWriter(new FileOutputStream(file), charset);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			throw new RuntimeIOException(e);
		} 
	}
	
	public FileWriter(File file) {
		this(file, "utf-8");
	}
	
	public void print(String s) {
		try {
			os.write(s);
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}
	
	public void print(int val) {
		print(String.valueOf(val));
	}

	public void print(Object val) {
		print(val.toString());
	}
	
	public void println() {
		print("\n");
	}
	
	public void println(String s) {
		print(s + "\n");
	}

	public void println(int val) {
		print(val + "\n");
	}

	public void println(Object val) {
		print(val + "\n");
	}

	@Override
	public void close() {
		try {
			os.close();
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

}
