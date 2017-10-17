package core.logger;

public interface ILogger {

	public void error(String format, Object...args);
	public void info(String format, Object...args);
	
}
