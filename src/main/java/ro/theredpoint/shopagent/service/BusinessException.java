package ro.theredpoint.shopagent.service;

/**
 * @author Radu DELIU
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public BusinessException(String message) {
		super(message);
	}
}