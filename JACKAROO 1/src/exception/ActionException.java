package exception;

@SuppressWarnings("serial")
public abstract class ActionException extends GameException {
	
	public ActionException() {
        super();
    }
	
    public ActionException(String message) {
        super(message);
    }

}
