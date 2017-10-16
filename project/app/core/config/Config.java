package core.config;

import play.Play;

public final class Config {

	public static String getString(String key) {
		return Play.configuration.getProperty(key);
	}
	
}
