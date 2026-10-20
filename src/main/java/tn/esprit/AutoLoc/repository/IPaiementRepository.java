package tn.esprit.AutoLoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.AutoLoc.domain.Paiement;

public interface IPaiementRepository extends JpaRepository<Paiement, Long> {
}
