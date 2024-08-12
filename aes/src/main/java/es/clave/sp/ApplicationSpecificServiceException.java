package es.clave.sp;

public class ApplicationSpecificServiceException extends RuntimeException {
	
	private static final long serialVersionUID = -3431663648133680058L;
	private String msg;
	private String title;
	
	public ApplicationSpecificServiceException(String title, String msg) {
		this.msg = msg;
		this.title = title;
	}
	
	public ApplicationSpecificServiceException() {
		super();
	}

	public ApplicationSpecificServiceException(String message) {
		super(message);
	}

	public ApplicationSpecificServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	public String getMessage() {
		return msg;
	}
	
	public String getTitle() {
		return title;
	}
	

}