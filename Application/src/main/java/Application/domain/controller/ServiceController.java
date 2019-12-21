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

import Application.domain.model.Professor;
import Application.domain.model.Student;
import Application.domain.service.ApplicationService;
import Application.domain.service.ApplicationRule;
import Application.domain.service.LoginService;
import Application.domain.service.SignIn;


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
    
	
	
	@RequestMapping(value = "/main/application", method = RequestMethod.GET) // 
    public String applicationView(Principal principal , Model model) 
	{	
		String userID = principal.getName();
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

	
	@RequestMapping(value = "/main/application/cancel", method = RequestMethod.GET) // 
    public String cancelApplication (String classCode , Principal principal, Model model) {
        // TODO implement here
		String userID = principal.getName();
		System.out.println("************************class code : "+ classCode);
		int code = Integer.parseInt(classCode);
		
		
		
		String result = applicationService.cancel(code, userID);
		
        model.addAttribute("result",result);
		int point = applicationService.getMyPoint(userID);
		model.addAttribute("point",point);
		List<Application.domain.model.Class> list = applicationService.enrolClassList(userID);
        model.addAttribute("list", list);
        return "Application";
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
    	String userID = principal.getName();
    	System.out.println("userID : "+ userID);
    	model.addAttribute("user",userID);
    	return "main";
    }
   
}