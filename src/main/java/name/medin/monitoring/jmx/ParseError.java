package name.medin.monitoring.jmx;

public class ParseError extends Exception {

	private static final long serialVersionUID = 1L;

	public ParseError(Exception cause){
		super(cause);
	}

	public ParseError(String string) {
		super(string);
	}

	
}
