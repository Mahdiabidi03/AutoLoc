package tn.esprit.AutoLoc.service;

import java.util.List;
import tn.esprit.AutoLoc.web.dto.EquipementDTO;
import tn.esprit.AutoLoc.web.dto.VehiculeDTO;

public interface IVehiculeService {
    VehiculeDTO create(VehiculeDTO dto);
    VehiculeDTO findById(Long id);
    List<VehiculeDTO> findAll();
    VehiculeDTO update(Long id, VehiculeDTO dto);
    void deleteById(Long id);
    VehiculeDTO affecterAAgence(Long idVehicule, Long idAgence);
    VehiculeDTO affecterEquipementAVehicule(Long idVehicule, EquipementDTO equipementDTO);
}
