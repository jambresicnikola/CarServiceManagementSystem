package hr.tvz.carservicemanagementsystem.exception;

public class RememberMeException extends RuntimeException {
    public RememberMeException(String message) {
        super(message);
    }

    public RememberMeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RememberMeException(Throwable cause) {
        super(cause);
    }

    public RememberMeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RememberMeException() {
    }
}
