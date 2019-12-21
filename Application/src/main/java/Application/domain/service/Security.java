package Application.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

@Component
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
		
		http.sessionManagement()           //  다중로그인 차단 설정은 해놨는데 잘 안되는거 같음 
		.maximumSessions(1)
		.maxSessionsPreventsLogin(false);  
 
		http.logout()
		.logoutUrl("/logout")
		.logoutSuccessUrl("/login")
		.permitAll();
	}
	@Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(loginService);
    }
}
