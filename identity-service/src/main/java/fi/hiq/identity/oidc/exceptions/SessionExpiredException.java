package fi.hiq.identity.oidc.exceptions;

public class SessionExpiredException extends RuntimeException {

	private static final long serialVersionUID = 7007741208266268578L;

	public SessionExpiredException(String message) {
		super(message);
	}

	public SessionExpiredException(String message, Throwable cause) {
		super(message, cause);
	}

}
