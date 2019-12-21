package Application.domain.service;

import java.util.*;

import Application.domain.model.Class;
import Application.domain.model.Professor;
import Application.domain.model.Enrol;
import Application.domain.model.Professor;
import Application.domain.model.Student;

/**
 * 
 */
public class AuthorityCheck {                    // 일단은 간단하게 이름과 코드만으로 권한 체크   , 추가적인 규칙 더하기 가능 

    public AuthorityCheck() {
    	
    }
    public boolean updateClassAuthority(Class c , Professor p) {
        if(c.getP_name().equals(p.getName()))
        {
        	return true;
        }
        else
        	return false;
    }
    
    public boolean cancelAuthority(Enrol e , Student s) {
        if(e.getStudentCode() == s.getCode())
        	return true;
        else 
        	return false;
    }

}