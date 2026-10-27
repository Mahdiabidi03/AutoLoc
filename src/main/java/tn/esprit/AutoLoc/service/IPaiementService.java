package tn.esprit.AutoLoc.service;

import java.util.List;
import tn.esprit.AutoLoc.domain.Paiement;

public interface IPaiementService {
    Paiement findById(Long id);
    List<Paiement> findAll();
}