package olimpiadastokyo.exceptions;

import olimpiadastokyo.services.EtapasEnum;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

/**
 * Created by lnsr on 11/8/2017.
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String PROP_ETAPA = "etapa";

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new RestApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String prop = ex.getBindingResult().getFieldError().getField();
        Object objRejectedValue = ex.getBindingResult().getFieldError().getRejectedValue();
        String rejectedValue = objRejectedValue == null ? null : objRejectedValue.toString();
        String errorMessage = "";

        if (rejectedValue == null || rejectedValue.isEmpty())
            errorMessage = String.format("Value for property '%s' cannot be null or empty", prop);
        else if (prop.equals(PROP_ETAPA)) {
            errorMessage = String.format("Value for property '%s' not valid. List of valid values: %s", PROP_ETAPA, EtapasEnum.getValues());
        }

        return buildResponseEntity(new RestApiError(HttpStatus.BAD_REQUEST, errorMessage, ex));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        RestApiError apiError = new RestApiError(HttpStatus.FORBIDDEN);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        RestApiError apiError = new RestApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(RuleBrokenException.class)
    protected ResponseEntity<Object> handleRuleBroken(RuleBrokenException ex) {
        RestApiError apiError = new RestApiError(HttpStatus.FORBIDDEN);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(RestApiError apiError) {
        return new ResponseEntity<Object>(apiError, apiError.getStatus());
    }

}