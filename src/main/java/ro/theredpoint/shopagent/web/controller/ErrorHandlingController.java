package ro.theredpoint.shopagent.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Simple {@link ErrorController} that is sending back to the user a json that contains an error message. 
 * 
 * @author Radu DELIU
 */
@RestController
public class ErrorHandlingController implements ErrorController {

	private static final String PATH = "/error";
	private ErrorAttributes errorAttributes;

	public ErrorHandlingController(ErrorAttributes errorAttributes) {
		this.errorAttributes = errorAttributes;
	}

	@RequestMapping(value = PATH)
    public String error(HttpServletRequest request) {
		
		Map<String, Object> errors = getErrorAttributes(request, false);

		return (String) errors.get("error");
    }
	
	@Override
	public String getErrorPath() {
		return PATH;
	}
	
	private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		
		return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
	}
}