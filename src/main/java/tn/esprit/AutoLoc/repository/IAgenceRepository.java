package tn.esprit.AutoLoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.AutoLoc.domain.Agence;

public interface IAgenceRepository extends JpaRepository<Agence, Long> {
}
