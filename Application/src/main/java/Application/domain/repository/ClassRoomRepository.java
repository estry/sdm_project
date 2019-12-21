package Application.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Application.domain.model.Class;

public interface ClassRoomRepository extends JpaRepository<Class, Integer> 
{
	Class findByCode(int code);
}
