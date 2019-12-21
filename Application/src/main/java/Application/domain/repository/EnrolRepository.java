package Application.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Application.domain.model.Enrol;

public interface EnrolRepository extends JpaRepository<Enrol, Integer>
{
	List<Enrol> findAllByStudentCodeOrderByClassCode(int stuentCode);
	Enrol findByStudentCode(int studentCode);
}
