package base.io;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

import base.core.ICloseable;
import base.core.Core;

public class LineReader implements ICloseable{

	public static final int SkipEmpty = 1;
	public static final int Trim = 2;
	
	private final Scanner scanner;
	private final boolean skip;
	private final boolean trim;
	
	public LineReader(Path path){
		this(path, 0);
	}
	
	public LineReader(Path path, int mask){
		try {
			this.scanner = new Scanner(path, "utf-8");
			this.skip = (mask & SkipEmpty) != 0;
			this.trim = (mask & Trim) != 0;
		} catch (IOException e) {
			throw Core.throwException(e);
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
