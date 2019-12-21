package Application.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Application.domain.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> 
{
	Student findByID(String ID);
}
