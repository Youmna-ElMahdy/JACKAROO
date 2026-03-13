package exception;

@SuppressWarnings("serial")
public class IllegalDestroyException extends ActionException {

    public IllegalDestroyException() {
        super();
    }

    public IllegalDestroyException(String message) {
        super(message);
    }

}
