package fi.hiq.identity.oidc.domain;

public class OidcDemoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public OidcDemoException(String message) {
        super(message);
    }

    public OidcDemoException(String message, Throwable rootCause) {
        super(message, rootCause);
    }
}
