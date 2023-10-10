package cat.tecnocampus.fgcstations.application.exception;

public class SameOriginDestinationException extends RuntimeException {
    public SameOriginDestinationException(String message) {
        super(message);
    }
}