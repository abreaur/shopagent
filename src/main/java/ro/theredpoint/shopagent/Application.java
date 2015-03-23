package ro.theredpoint.shopagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

import ro.theredpoint.shopagent.web.controller.ErrorHandlingController;

@ComponentScan(excludeFilters = {@Filter(
		value = ErrorHandlingController.class, type = FilterType.ASSIGNABLE_TYPE)})
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}