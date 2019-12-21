package Application.domain.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import Application.domain.model.Professor;
import Application.domain.model.Student;
import Application.domain.repository.ProfessorRepository;
import Application.domain.repository.StudentRepository;

@Service
public class LoginService implements AuthenticationProvider {

	@Autowired
	ProfessorRepository professorRepository;
	@Autowired
	StudentRepository studentRepository;
	
    public String login( String id, String pw) {
        Student s = studentRepository.findByID(id);
        Professor p = professorRepository.findByID(id);
        if(s!=null)
        {
        	if(s.getPW().equals(pw))
        	{
        		
        		return "login success";
        	}
        	else 
        		return "login fail by password";
        }
        else if(p!=null) 
        {
        	if(p.getPassword().equals(pw))
        	{
        		
        		return "login success";
        	}
        	else 
        		return "login fail by password";
        }
        else 
        {
        	return "login fail , can't find user";
        }
    }

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {     // 스프링 시큐리티가 로그인 요청을 가로채서 이쪽으로 가져온다 
		String id = authentication.getName();
        String pw = (String) authentication.getCredentials();
 
        String result = login(id, pw);
        if(!result.contains("success"))
        {
        	if(result.contains("password"))
        		throw new BadCredentialsException("로그인 실패\n 패스워드 오류!!");
        	else if (result.contains("can't find"))
        		throw new BadCredentialsException("로그인 실패\n 사용자를 찾을 수 없음!!");
        }
        else 
        {
        	result = id;           //result에 있는 값을 사용자 id로 바꿔줌  이래야 나중에 principal에 userID가 들어간다.
        }
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(result, null, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}