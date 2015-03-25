package ro.theredpoint.shopagent.web.config;

import java.util.List;

import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.gson.GsonBuilder;

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
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

		super.configureMessageConverters(converters);
		
		GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
		gsonHttpMessageConverter.setGson(new GsonBuilder().setPrettyPrinting().create());
		converters.add(gsonHttpMessageConverter);
	}
}