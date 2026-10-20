package tn.esprit.AutoLoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.AutoLoc.domain.Employe;

public interface IEmployeRepository extends JpaRepository<Employe, Long> {
}
