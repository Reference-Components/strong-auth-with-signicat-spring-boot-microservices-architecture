package fi.hiq.identity.oidc.exceptions;

public class CommonOidcException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CommonOidcException(String message) {
        super(message);
    }

    public CommonOidcException(String message, Throwable rootCause) {
        super(message, rootCause);
    }
}
