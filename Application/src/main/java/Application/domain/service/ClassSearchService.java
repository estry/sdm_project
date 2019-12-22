package Application.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import Application.domain.repository.ClassRoomRepository;
import Application.domain.model.Class;

@Service
public class ClassSearchService 
{
	@Autowired
	ClassRoomRepository classRepository;
	
	public List<Application.domain.model.Class> findAllClassByDept(String dept)     // 같은 학과에 있는 수업 다 찾기 
	{
		List<Class> list = classRepository.findAll();
		List<Class> deptList = new ArrayList<Class>();
		for(int i=0;i<list.size();i++)
		{
			if(list.get(i).getDept().equals(dept))
			{
				deptList.add(list.get(i));
			}
		}
		return deptList;
	}
	public List<Application.domain.model.Class> findAll()     // 모든 수업 다 찾기 
	{
		return classRepository.findAll();
	}
	public List<Application.domain.model.Class> findOne(int code)   // 리턴 타입 맞출려고 일부로 리스트로 반환 
	{
		List<Class> list = new ArrayList<Class>();
		list.add(classRepository.findByCode(code));
		if(list.size()==0)
			return null;
		else 
			return list;
	}
	
}
