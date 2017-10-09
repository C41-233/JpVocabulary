package core.controller.validation;

public class NoSuchValidationException extends RuntimeException{

	public NoSuchValidationException() {
		super();
	}
	
	public NoSuchValidationException(String msg) {
		super(msg);
	}
	
}
