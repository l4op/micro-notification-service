package it.loooop.exception;
import java.io.Serializable;

public class ValidationException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -550591133680547573L;
    private final String code;

    public ValidationException(String code) {
        super();
        this.code = code;
    }

    public ValidationException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }

    public ValidationException(String message, String code) {
        super(message);
        this.code = code;
    }

    public ValidationException(Throwable cause, String code) {
        super(cause);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
