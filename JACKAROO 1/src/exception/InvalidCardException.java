package exception;

@SuppressWarnings("serial")
public class InvalidCardException extends InvalidSelectionException {

    public InvalidCardException() {
        super();
    }

    public InvalidCardException(String message) {
        super(message);
    }

}
