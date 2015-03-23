package ro.theredpoint.shopagent.web.config;

import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ro.theredpoint.shopagent.web.controller.ErrorHandlingController;

/**
 * @author Radu DELIU
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Bean
	public ErrorHandlingController basicErrorController(ErrorAttributes errorAttributes) {
		return new ErrorHandlingController(errorAttributes);
	}
}