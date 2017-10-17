package core.logger;

public interface ILogger {

	public void debug(String format, Object...args);
	public void warn(String format, Object...args);
	public void error(String format, Object...args);
	public void info(String format, Object...args);
	
}
