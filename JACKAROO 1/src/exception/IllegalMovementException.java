package exception;

@SuppressWarnings("serial")
public class IllegalMovementException extends ActionException {

    public IllegalMovementException() {
        super();
    }

    public IllegalMovementException(String message) {
        super(message);
    }

}
