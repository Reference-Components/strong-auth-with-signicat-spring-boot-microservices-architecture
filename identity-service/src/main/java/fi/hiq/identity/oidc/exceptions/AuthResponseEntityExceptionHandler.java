package fi.hiq.identity.oidc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import fi.hiq.identity.oidc.dto.ErrorResponseDTO;

@ControllerAdvice
public class AuthResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { IllegalParameterException.class, SessionExpiredException.class })
	public ResponseEntity<ErrorResponseDTO> handleCustomDataNotFoundExceptions(Exception e) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
	        
        return new ResponseEntity<>(new ErrorResponseDTO(status, e.getMessage()), status);
	}
}
