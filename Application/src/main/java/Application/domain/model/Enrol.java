package Application.domain.model;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Enrol {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int code;
    private int studentCode;
    private int classCode;
    public Enrol() 
    {
    	
    }

    public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}

	public int getStudentCode() {
		return studentCode;
	}

	public void setStudentCode(int studentCode) {
		this.studentCode = studentCode;
	}

	public int getClassCode() {
		return classCode;
	}

	public void setClassCode(int classCode) {
		this.classCode = classCode;
	}

}