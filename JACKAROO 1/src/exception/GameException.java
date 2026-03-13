package exception;

@SuppressWarnings("serial")
public abstract class GameException extends Exception {

	public GameException() {
        super();
    }
	
    public GameException(String message) {
        super(message);
    }
}
