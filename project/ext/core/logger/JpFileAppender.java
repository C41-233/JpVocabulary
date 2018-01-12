package core.logger;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.joda.time.DateTime;

import c41.core.Core;
import c41.io.FileWriter;
import c41.utility.Strings;

public class JpFileAppender extends AppenderSkeleton {

	private String encoding;
	private String path;
	private boolean debug;
	
	private File file; 
	private FileWriter writer;

	@Override
	public void append(LoggingEvent e) {
		if(writer == null) {
			this.writer = new FileWriter(file, true);
		}
		String output = layout.format(e);
		writer.print(output);
		writer.flush();
	}

	@Override
	public void close() {
		if(writer != null) {
			writer.close();
		}
		this.writer = null;
		this.closed = true;
	}

	@Override
	public void activateOptions() {
		if(encoding == null) {
			encoding = "utf-8";
		}
		if(path == null) {
			path = "";
		}
		
		File folder = new File(path);
		if(folder.exists() == false) {
			folder.mkdir();
		}
		
		if(folder.isDirectory() == false) {
			Core.throwException(new IOException("无法创建目录："+path));
		}
		
		DateTime now = DateTime.now();
		String day = now.toString("yyyy-MM-dd");
		String filename = Strings.format("%s.log", day);
		this.file = new File(folder, filename);
	}
	
	@Override
	public boolean requiresLayout() {
		return true;
	}
	
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getEncoding() {
		return this.encoding;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public void setDebug(boolean value) {
		this.debug = value;
	}
	
	public boolean getDebug() {
		return this.debug;
	}
	
}


