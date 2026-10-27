package tn.esprit.AutoLoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.AutoLoc.domain.Contrat;

public interface IContratRepository extends JpaRepository<Contrat, Long> {
}