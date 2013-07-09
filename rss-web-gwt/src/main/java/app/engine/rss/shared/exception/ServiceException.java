package app.engine.rss.shared.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1362944194708201062L;

	public ServiceException(String msg, Throwable ex) {
		super(msg, ex);
	}

	public ServiceException() {
	};

}
