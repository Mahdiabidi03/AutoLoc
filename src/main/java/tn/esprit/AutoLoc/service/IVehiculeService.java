package tn.esprit.AutoLoc.service;

import java.util.List;
import tn.esprit.AutoLoc.domain.Vehicule;
import tn.esprit.AutoLoc.web.dto.VehiculeResponse;

public interface IVehiculeService {
    Vehicule create(Vehicule vehicule);
    Vehicule findById(Long id);
    List<Vehicule> findAll();
    Vehicule update(Long id, Vehicule vehicule);
    void deleteById(Long id);
    Vehicule affecterAAgence(Long idVehicule, Long idAgence);
    VehiculeResponse affecterEquipement(Long idVehicule, Long idEquipement);
}