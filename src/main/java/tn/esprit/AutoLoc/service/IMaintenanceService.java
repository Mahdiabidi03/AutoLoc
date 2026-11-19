package tn.esprit.AutoLoc.service;

import java.util.List;
import tn.esprit.AutoLoc.domain.Maintenance;
import tn.esprit.AutoLoc.web.dto.MaintenanceResponse;

public interface IMaintenanceService {
    Maintenance create(Maintenance maintenance);
    Maintenance findById(Long id);
    List<Maintenance> findAll();
    Maintenance update(Long id, Maintenance maintenance);
    void deleteById(Long id);
    MaintenanceResponse affecterAVehicule(Long idMaintenance, Long idVehicule);
}