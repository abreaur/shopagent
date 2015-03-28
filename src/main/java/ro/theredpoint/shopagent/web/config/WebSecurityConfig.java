package ro.theredpoint.shopagent.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Radu DELIU
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private UserDetailsService userDetailsService;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf()
        		.disable()
            .authorizeRequests()
                .antMatchers("/javascripts/*", "/stylesheets/*", "/images/*").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
	            .defaultSuccessUrl("/index.html")
            	.loginProcessingUrl("/login")
                .loginPage("/login.html")
                .permitAll()
                .and()
            .logout()
                .permitAll()
                .logoutSuccessUrl("/login.html");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService);
    }
}