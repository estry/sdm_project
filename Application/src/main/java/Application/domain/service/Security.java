package Application.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter
{
	
	@Autowired
    private LoginService loginService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.authorizeRequests()
		.anyRequest().authenticated()
        .and()
        .httpBasic()
        .and()
        .formLogin()
        .defaultSuccessUrl("/main")
        .permitAll();
		
		http.sessionManagement()           
		.maximumSessions(1)
		.maxSessionsPreventsLogin(false)
		.expiredUrl("/login");  
		//.sessionRegistry(sessionRegistry);
 
		http.logout()
		.logoutUrl("/logout")
		.invalidateHttpSession(true)
		.logoutSuccessUrl("/login")
		.permitAll();
	}
	/*        // 세션 삭제용으로 넣었는데 뭔가 잘 안됨
	@Bean
	public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
	    return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
	}
	*/                                                                    
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(loginService);
    }
}
