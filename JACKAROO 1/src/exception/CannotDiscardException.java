package exception;

@SuppressWarnings("serial")
public class CannotDiscardException extends ActionException {

    public CannotDiscardException() {
        super();
    }

    public CannotDiscardException(String message) {
        super(message);
    }

}
