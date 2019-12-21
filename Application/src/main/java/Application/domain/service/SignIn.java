package Application.domain.service;

import java.util.*;

/**
 * 
 */
public class SignIn {

    /**
     * Default constructor
     */
    public SignIn() {
    }

   
    private String ID;

    private String PW;

    
    private String name;

    private int code;

    private String job;
   
    private String viewName = "SignIn";


    public void checkIsVaild( String id,  String pw) {
        // TODO implement here
    }

    public boolean checkClientAlreadyExist(int code, String job) {
        // TODO implement here
        return false;
    }
    public void signIn( String id,  String pw,  String name, String code, String job) {
        // TODO implement here
    }

    /**
     * @return
     */
    public String returnViewInfo() {
        // TODO implement here
        return "";
    }

}