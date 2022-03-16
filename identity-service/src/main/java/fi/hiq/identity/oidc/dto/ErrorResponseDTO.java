package fi.hiq.identity.oidc.dto;

import org.springframework.http.HttpStatus;

public class ErrorResponseDTO {
	private HttpStatus status;
    private String message;

    public ErrorResponseDTO(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(final HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
