package fi.hiq.identity.oidc.exceptions;

public class IllegalParameterException extends RuntimeException {

	private static final long serialVersionUID = -8654907737487734888L;

	public IllegalParameterException(String message) {
		super(message);
	}

	public IllegalParameterException(String message, Throwable cause) {
		super(message, cause);
	}

}
