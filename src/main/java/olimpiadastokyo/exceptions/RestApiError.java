package olimpiadastokyo.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * Created by lnsr on 11/8/2017.
 */

@Data
public class RestApiError {
    private HttpStatus status;
    private Date time;
    private String message;
    private String debugMessage;

    private RestApiError() {
        time = new Date();
    }

    RestApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    RestApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    RestApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
}
