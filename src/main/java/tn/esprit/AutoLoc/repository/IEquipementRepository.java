package tn.esprit.AutoLoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.AutoLoc.domain.Equipement;

public interface IEquipementRepository extends JpaRepository<Equipement, Long> {
}
