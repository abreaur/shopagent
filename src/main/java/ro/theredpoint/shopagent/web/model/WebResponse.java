package ro.theredpoint.shopagent.web.model;

/**
 * @author Radu DELIU
 */
public class WebResponse<T> {

	private boolean successful;
	private String error;
	private T data;
	
	public WebResponse(T data) {
		
		this.data = data;
		this.successful = true;
	}

	public WebResponse(String error) {
		
		this.error = error;
		this.successful = false;
	}

	public boolean isSuccessful() {
		return successful;
	}
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}