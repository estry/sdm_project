package Application.domain.service;

import java.util.*;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import Application.domain.model.Class;
import Application.domain.model.Professor;
import Application.domain.model.Student;

@Service 
public class ApplicationRule         // singleton 패턴 적용 (여러번 사용하고 하나만 있어도 상관 없는 객체 , 값을 변경하는것도 없어서 레이스 컨디션도 상관 없음)
{
    private ApplicationRule()
    {
    	
    }
    private static ApplicationRule rule = new ApplicationRule();
    
    public boolean isClassGradeOK(Class c , Student s) {
        if(c.getGrade() == s.getGrade() || Math.abs(c.getGrade() - s.getGrade()) == 1) // 같은 학년이거나 1학년 차이나야 수강 가능    2학년이 3학년 과목 수강 ok  하지만 2학년이 4학년 과목 수강 x 
        {
        	return true;
        }
        else 
        	return false;
    }

    public boolean isStudentPointRemain(Class c , Student s) {    // 수강 학점이 남아있는지 검사 
    	
    	if(s.getPoint() - c.getPoint() >= 0)
    		return true;
    	else 
    		return false;
    }

    public boolean isClassEmpty(Class c) {     // 수업에 빈 자리가 있는지 검사 
    	
        if(c.getCurrent_num() < c.getLimit_num())
        	return true;
        else 
        	return false;
    }

    public boolean isClassTimeOverlap(Class c, Student s) // 수업시간이 겹치는지 검사    아직 구현 덜됨 
    {     
        
        return false;
    }
    public static ApplicationRule getInstance()
    {
    	return rule;
    }

}