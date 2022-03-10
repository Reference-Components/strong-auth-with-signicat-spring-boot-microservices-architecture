package fi.hiq.gateway.dto;

import org.springframework.http.HttpStatus;

public class ErrorResponseDto {

	private HttpStatus status;
    private String message;

    public ErrorResponseDto(final HttpStatus status, final String message) {
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
