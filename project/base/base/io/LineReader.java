package base.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import base.core.ICloseable;

public class LineReader implements ICloseable{

	public static final int SkipEmpty = 1;
	public static final int Trim = 2;
	
	private final Scanner scanner;
	private final boolean skip;
	private final boolean trim;
	
	public LineReader(File file){
		this(file, 0);
	}
	
	public LineReader(File file, int mask){
		try {
			this.scanner = new Scanner(file);
			this.skip = (mask & SkipEmpty) != 0;
			this.trim = (mask & Trim) != 0;
		} catch (FileNotFoundException e) {
			throw new RuntimeIOException(e);
		}
	}
	
	public String readLine(){
		while(scanner.hasNext()){
			String line = scanner.nextLine();
			if(trim){
				line = line.trim();
			}
			if(skip && line.isEmpty()){
				continue;
			}
			return line;
		}
		return null;
	}
	
	@Override
	public void close() {
		this.scanner.close();
	}

}
