package core.config;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import play.Play;

public final class Config {

	public static String getString(String key) {
		return Play.configuration.getProperty(key);
	}
	
	public static Path getPath(String key) {
		String value = getString(key);
		if(value == null) {
			return null;
		}
		return FileSystems.getDefault().getPath(value);
	}
	
	public static final boolean IsDebug = Play.id.equals("debug");
	
}
