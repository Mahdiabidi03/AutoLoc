package tn.esprit.AutoLoc.repository;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.AutoLoc.domain.Contrat;
public interface IContratRepository extends CrudRepository<Contrat, Long> {
}