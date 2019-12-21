package Application.domain.model;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 */
@Entity
public class Professor {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int professorId;
    
    protected int code;

    protected String job;
    
    protected String name;

    protected String dept;

    //protected String schedule;   // 배열로 만들기 

    protected String ID;

    protected String PW;

    public void setCode(int c)
    {
    	this.code = c;
    }
    public int getCode()
    {
    	return this.code;
    }
    public void setJob(String job)
    {
    	this.job = job;
    }
    public String getJob()
    {
    	return this.job;
    }
    public void setName(String n)
    {
    	this.name = n;
    }
    public String getName()
    {
    	return this.name;
    }
    public void setDept(String d)
    {
    	this.dept = d;
    }
    public String getDept()
    {
    	return this.dept;
    }
    public void setID(String id)
    {
    	this.ID = id;
    }
    public String getID()
    {
    	return this.ID;
    }
    public void setPassword(String p)
    {
    	this.PW = p;
    }
    public String getPassword()
    {
    	return this.PW;
    }
	
   
}