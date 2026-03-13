package exception;

@SuppressWarnings("serial")
public class IllegalSwapException extends ActionException {

    public IllegalSwapException() {
        super();
    }

    public IllegalSwapException(String message) {
        super(message);
    }
}
