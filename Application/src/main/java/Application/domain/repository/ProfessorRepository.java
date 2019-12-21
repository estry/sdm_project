package Application.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Application.domain.model.Professor;
import Application.domain.model.Student;

public interface ProfessorRepository extends JpaRepository<Professor, Integer>
{
	Professor findByID(String ID);
}
