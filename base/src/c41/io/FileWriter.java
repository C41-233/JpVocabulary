package c41.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import c41.core.Core;
import c41.core.ICloseable;

public class FileWriter implements ICloseable{

	private final OutputStreamWriter os;
	
	public FileWriter(File file, String charset, boolean append) {
		try {
			this.os = new OutputStreamWriter(new FileOutputStream(file, append), charset);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			throw Core.throwException(e);
		} 
	}
	
	public FileWriter(File file, boolean append) {
		this(file, "utf-8", append);
	}
	
	public FileWriter(File file) {
		this(file, "utf-8", false);
	}
	
	public void print(String s) {
		try {
			os.write(s);
		} catch (IOException e) {
			Core.throwException(e);
		}
	}
	
	public void print(int val) {
		print(String.valueOf(val));
	}

	public void print(boolean val) {
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

	public void println(boolean val) {
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
			Core.throwException(e);
		}
	}

	public void flush() {
		try {
			this.os.flush();
		} catch (IOException e) {
			Core.throwException(e);
		}
	}

}
