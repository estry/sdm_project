package Application.domain.service;
import Application.domain.model.Class;
import Application.domain.model.Enrol;
import Application.domain.model.Student;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Application.domain.repository.ClassRoomRepository;
import Application.domain.repository.EnrolRepository;
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
	 @Autowired
	 private EnrolRepository enrolRepository;
	 
	 
	 @Transactional
	 public String enrol(int classCode , String userID)
	 {
		 Class target = null;
		 target = classRepository.findByCode(classCode);
		 Student user = null;
		 user = studentRepository.findByID(userID);
		 if(target == null)                                  //조건 검사 부분 
			 return "수업을 찾을 수 없습니다.";
		 if(user == null)
			 return "사용자가 학생이 아닙니다.";
		 if(!rule.isClassCodeOverlap(target, user))
			 return "이미 신청한 수업입니다.";
		 if(!rule.isClassEmpty(target))
			 return "수강인원이 다 찼습니다.";
		 if(!rule.isClassGradeOK(target, user))
			 return "해당 학년이 들을 수 없는 수업입니다.";
		 if(!rule.isStudentPointRemain(target, user))
			 return "잔여 학점이 모자랍니다.";
		 
		 //if(!rule.isClassTimeOverlap(target, user))       
			// return "error : classtime is overlap"; 
		 
		 
		 
		 
		 user.setPoint(user.getPoint()-target.getPoint());  // 신청가능한 학점 차감 
		 // user 시간표 변경 코드 작성해야됨
		 target.setCurrent_num(target.getCurrent_num()+1);  // 현재 수강인원 + 1
		 
		 Enrol newEnrol = new Enrol();
		 newEnrol.setClassCode(target.getCode());
		 newEnrol.setStudentCode(user.getCode());
		 
		 enrolRepository.saveAndFlush(newEnrol);
		 studentRepository.saveAndFlush(user);   // 데이터베이스에 변경 사항 저장
		 classRepository.saveAndFlush(target);
		 return "enrol success";
			 
	 }
	 
	 @Transactional
	 public String cancel(int classCode , String userID)   // 수강 취소 기능 
	 {
		 Student user = null;
		 user = studentRepository.findByID(userID);
		 if(user!=null)
		 {
			 List<Enrol> enrolList = enrolRepository.findAllByStudentCodeOrderByClassCode(user.getCode());
			 for(int i=0;i<enrolList.size();i++)
			 {
				if(enrolList.get(i).getClassCode() == classCode)
				{
					Class target = classRepository.findByCode(classCode);
					if(target == null)
						return "수강 취소 실패 - 수업을 찾을 수 없음";
					user.setPoint(user.getPoint() + target.getPoint());
					target.setCurrent_num(target.getCurrent_num() - 1);
					
					enrolRepository.delete(enrolList.get(i));
					studentRepository.saveAndFlush(user);   // 데이터베이스에 변경 사항 저장
					classRepository.saveAndFlush(target);
					return "수강 취소 성공";
				}
			 }
		 }
		 return "수강 취소 실패";
	 }
	 
	 @Transactional
	 public List<Class> enrolClassList(String userID)
	 {
		 Student user = null;
		 user = studentRepository.findByID(userID);
		 if(user!=null)
		 {
			 List<Enrol> enrolList = enrolRepository.findAllByStudentCodeOrderByClassCode(user.getCode());
			 List<Class> classList = new ArrayList<Class>();
			 for(int i=0;i<enrolList.size();i++)
			 {
				classList.add(classRepository.findByCode(enrolList.get(i).getClassCode()));
			 }
			 return classList;
		 }
		 else 
			 return null;
	 }
	 public int getMyPoint(String userID)
	 {
		 Student user = null;
		 user = studentRepository.findByID(userID);
		 if(user!=null)
		 {
			 return user.getPoint();
		 }
		 else 
			 return -1;
	 }
}
