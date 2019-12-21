package Application.domain.service;
import Application.domain.model.Class;
import Application.domain.model.Student;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Application.domain.repository.ClassRoomRepository;
import Application.domain.repository.StudentRepository;

@Service 
public class ApplicationService
{
	 @Autowired
	 private ApplicationRule rule;
	 @Autowired
	 private StudentRepository studentRepository;
	 @Autowired
	 private ClassRoomRepository classRepository;
	 
	 public String enrol(int classCode , String userID)
	 {
		 Class target = null;
		 target = classRepository.findByCode(classCode);
		 Student user = null;
		 user = studentRepository.findByID(userID);
		 if(target == null)                                  //조건 검사 부분 
			 return "error: can't find class";
		 if(user == null)
			 return "error: can't find user";
		 if(!rule.isClassEmpty(target))
			 return "error : class already full";
		 if(!rule.isClassGradeOK(target, user))
			 return "error : grade rule violation";
		 if(!rule.isStudentPointRemain(target, user))
			 return "error : user poing is not enough";
		 //if(!rule.isClassTimeOverlap(target, user))       
			// return "error : classtime is overlap"; 
		 
		 
		 
		 
		 user.setPoint(user.getPoint()-target.getPoint());  // 신청가능한 학점 차감 
		 // user 시간표 변경 코드 작성해야됨
		 target.setCurrent_num(target.getCurrent_num()+1);  // 현재 수강인원 + 1
		 
		 studentRepository.saveAndFlush(user);   // 데이터베이스에 변경 사항 저장
		 classRepository.saveAndFlush(target);
		 return "enrol success";
			 
		 
	 }
}
