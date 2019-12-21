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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public String applicationView() 
	{	
        return "Application";
    }
	@RequestMapping(value = "/main/application/{code}", method = RequestMethod.GET) // 
    public String application(@PathVariable("code")int classCode,Principal principal) 
	{	
		String userID = principal.getName();
		System.out.println("userID : "+ userID);
        applicationService.enrol(classCode, userID);
        return "Application"; // + 시간표 
    }

	
	@RequestMapping(value = "/main/cancel", method = RequestMethod.GET) // 
    public String cancelApplication () {
        // TODO implement here
        return "";
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
    	model.addAttribute("userName",userID);
    	return "main";
    }
}