package exceptions;

@SuppressWarnings("serial")
public class NoDBPropertiesException extends RuntimeException {

    public NoDBPropertiesException() {
    }

    public NoDBPropertiesException(String message) {
        super(message);
    }

    public NoDBPropertiesException(String message, Throwable cause) {
        super(message, cause);
    }
}
