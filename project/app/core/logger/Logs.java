package core.logger;

import org.apache.log4j.Logger;

import base.utility.Strings;
import core.config.Config;

public final class Logs {

	public static final ILogger Http = new JpLogger("http");
	public static final ILogger Db = new JpLogger("db");
	public static final ILogger Logic = new JpLogger("logic");
	
	private static class JpLogger implements ILogger{
		
		private final Logger logger;
		
		public JpLogger(String name) {
			if(Config.IsDebug) {
				name = "jp.debug."+name;
			}
			else {
				name = "jp." + name;
			}
			this.logger = Logger.getLogger(name);
		}
		
		@Override
		public void error(String format, Object...args) {
			this.logger.error(Strings.format(format, args));
		}

		@Override
		public void info(String format, Object... args) {
			this.logger.info(Strings.format(format, args));
		}

		@Override
		public void debug(String format, Object... args) {
			this.logger.debug(Strings.format(format, args));
		}

		@Override
		public void warn(String format, Object... args) {
			this.logger.warn(Strings.format(format, args));
		}
		
	}
	
}
