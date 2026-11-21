package tn.esprit.AutoLoc.service;

import java.util.List;
import tn.esprit.AutoLoc.web.dto.MaintenanceDTO;

public interface IMaintenanceService {
    MaintenanceDTO create(MaintenanceDTO dto);
    MaintenanceDTO findById(Long id);
    List<MaintenanceDTO> findAll();
    MaintenanceDTO update(Long id, MaintenanceDTO dto);
    void deleteById(Long id);
    MaintenanceDTO affecterMaintenanceAVehicule(Long idVehicule, MaintenanceDTO maintenanceDTO);
}
