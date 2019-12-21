package Application.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Application.domain.model.Enrol;

public interface EnrolRepository extends JpaRepository<Enrol, Integer>
{

}
