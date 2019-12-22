package Application.domain.controller;

import java.security.Principal;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Application.domain.service.ApplicationService;
import Application.domain.service.ClassSearchService;
import Application.domain.service.ApplicationRule;
import Application.domain.service.LoginService;
import Application.domain.service.SignIn;
import Application.domain.model.*;
import Application.domain.model.Class;
import Application.domain.repository.ProfessorRepository;
import Application.domain.repository.StudentRepository;

//@CrossOrigin(origins = {"*","http://221.155.56.120:8080" })   // 아직 제대로 안바꿈 
@Controller
public class ServiceController {
	
    
    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);
    
    /*
    @Autowired
    private SignIn sign;
    */
    @Autowired
    ApplicationService applicationService;
    @Autowired
    ClassSearchService classSearchService;
    @Autowired
    StudentRepository StudentRepository;
    @Autowired
    ProfessorRepository ProfessorRepository;
	
	@RequestMapping(value = "/main/application", method = RequestMethod.GET) // 
    public String applicationView(Principal principal , Model model) 
	{	
		String userID = principal.getName();
		List<Application.domain.model.Class> list = applicationService.enrolClassList(userID);
        model.addAttribute("list", list);
		int point = applicationService.getMyPoint(userID);
		model.addAttribute("point",point);
        return "Application";
    }
	@RequestMapping(value = "/main/application/submit", method = RequestMethod.GET) // 
    public String application(String code , Principal principal , Model model) 
	{	
		String userID = principal.getName();
		//System.out.println("classCode : "+ code);
		try {
			int classCode = Integer.parseInt(code);
			String result = applicationService.enrol(classCode, userID);
		       
	        if(result.contains("success"))
	        	result = "수강 신청 성공";
	        model.addAttribute("result",result);
	        int point = applicationService.getMyPoint(userID);
			model.addAttribute("point",point);
	        List<Application.domain.model.Class> list = applicationService.enrolClassList(userID);
	        model.addAttribute("list", list);
	        return "Application"; // + 시간표 
            
        } catch (NumberFormatException e) {                             // 과목 코드란에 숫자가 아닌 다른 형식 입력했을때 catch 
           String result = "잘못된 형식 입력 - 다시 입력하세요";
           model.addAttribute("result",result);
           int point = applicationService.getMyPoint(userID);
			model.addAttribute("point",point);
	        List<Application.domain.model.Class> list = applicationService.enrolClassList(userID);
	        model.addAttribute("list", list);
	        return "Application"; // + 시간표 
        }
		
        
    }

	
	@RequestMapping(value = "/main/application/cancel", method = RequestMethod.GET) // 일단 구현 완료
    public String cancelApplication (String classCode , Principal principal, Model model) {
        // TODO implement here
		String userID = principal.getName();
		
		try {
			int code = Integer.parseInt(classCode);
			String result = applicationService.cancel(code, userID);
		
			model.addAttribute("result",result);
			int point = applicationService.getMyPoint(userID);
			model.addAttribute("point",point);
			List<Application.domain.model.Class> list = applicationService.enrolClassList(userID);
        	model.addAttribute("list", list);
        	return "Application";
		}
		catch (NumberFormatException e) {                             // 과목 코드란에 숫자가 아닌 다른 형식 입력했을때 catch 
	           String result = "잘못된 형식 입력 - 다시 입력하세요";
	           model.addAttribute("result",result);
	           int point = applicationService.getMyPoint(userID);
			   model.addAttribute("point",point);
		       List<Application.domain.model.Class> list = applicationService.enrolClassList(userID);
		       model.addAttribute("list", list);
		       return "Application"; // + 시간표 
	    }
    }
	@RequestMapping(value = "/main/classInfo", method = RequestMethod.GET) 
	public String classInfoView()
	{
		return "classInfo";
	}
	
	
	
	@RequestMapping(value = "/main/classInfo/search", method = RequestMethod.GET)    // 구현 완료 
    public String classInfo (String type , String input , Model model) {
        // TODO implement here
		try {
			
		   if(type.equals("학과"))
		   {
			   List<Class> list = classSearchService.findAllClassByDept(input);
			   if(list!=null)
			   {
				   model.addAttribute("result","검색 성공 = "+list.size()+"개 결과");
				   model.addAttribute("list",list);
			   }
			   else 
				   model.addAttribute("result","검색 실패 - 존재하지 않음");   
		   }
		   else if(type.equals("전체"))
		   {
			   List<Class> list = classSearchService.findAll();
			   if(list!=null)
			   {
				   model.addAttribute("result","검색 성공 = "+list.size()+"개 결과");
				   model.addAttribute("list",list);
			   }
			   else 
				   model.addAttribute("result","검색 실패 - 존재하지 않음");   
		   }
		   else if(type.equals("코드"))
		   {
			   try {
				   int code = Integer.parseInt(input);
				   List<Class> list = classSearchService.findOne(code);
				   if(list!=null)
				   {
					   model.addAttribute("result","검색 성공 = "+list.size()+"개 결과");
					   model.addAttribute("list",list);
				   }
				   else 
					   model.addAttribute("result","검색 실패 - 존재하지 않음");
			   }
			   catch (NumberFormatException e) {     
				   model.addAttribute("result","잘못된 형식 입력- 다시 입력하세요");
				   return "classInfo";
			   }
			   
		   }
		   return "classInfo";
	        
            
        } catch (NumberFormatException e) {                             // 과목 코드란에 숫자가 아닌 다른 형식 입력했을때 catch 
           String result = "잘못된 형식 입력 - 다시 입력하세요";
           model.addAttribute("result",result);
	       return "classInfo"; 
        }
    }

    
    @RequestMapping(value = "/main/createClass", method = RequestMethod.GET) // 
    public String createClass( ) {
        // TODO implement here
        return "";
    }

   
    @RequestMapping(value = "/main/updateClass", method = RequestMethod.GET) // 
    public String updateClass() {
        // TODO implement here
        return "";
    }
    
   
    @RequestMapping(value = "/main/schedule", method = RequestMethod.GET) // 
    public String printSchedule() {
        // TODO implement here
        return "";
    }
 
    @RequestMapping(value = "/login", method = RequestMethod.GET)   // ok
    public String viewloginForm()
    {
    	return "loginForm";
    }
    @RequestMapping(value = "/main", method = RequestMethod.GET)   // ok
    public String loginSuccess(Model model,Principal principal)
    {
    	// 이름 출력하는 걸로 바꿈 
    	String userID = principal.getName();
    	Student s = StudentRepository.findByID(userID);
    	Professor p = ProfessorRepository.findByID(userID);
    	if(s!= null)
    	{
    		String userName = s.getName();
    		System.out.println("userName : "+ userName);
    		model.addAttribute("user",userName);
    	}
    	else if(s==null && p != null)
    	{
    		String userName = p.getName() + " 교수";
    		System.out.println("userName : "+ userName);
    		model.addAttribute("user",userName);
    	}
    	return "main";
    }
   
}