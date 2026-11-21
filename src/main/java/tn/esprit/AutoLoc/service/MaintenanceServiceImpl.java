package tn.esprit.AutoLoc.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.AutoLoc.domain.Maintenance;
import tn.esprit.AutoLoc.domain.Vehicule;
import tn.esprit.AutoLoc.exception.ResourceNotFoundException;
import tn.esprit.AutoLoc.mapper.MaintenanceMapper;
import tn.esprit.AutoLoc.repository.IMaintenanceRepository;
import tn.esprit.AutoLoc.repository.IVehiculeRepository;
import tn.esprit.AutoLoc.web.dto.MaintenanceDTO;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final MaintenanceMapper maintenanceMapper;

    @Override
    @Transactional
    public MaintenanceDTO create(MaintenanceDTO dto) {
        if (dto.getIdMaintenance() != null) {
            throw new IllegalArgumentException("Une nouvelle maintenance ne doit pas avoir d'identifiant");
        }
        validerDates(dto.getDateDebut(), dto.getDateFin());
        Maintenance maintenance = maintenanceMapper.toEntity(dto);
        if (dto.getIdVehicule() != null) {
            maintenance.setVehicule(getVehicule(dto.getIdVehicule()));
        }
        return maintenanceMapper.toDTO(maintenanceRepository.save(maintenance));
    }

    @Override
    @Transactional(readOnly = true)
    public MaintenanceDTO findById(Long id) {
        return maintenanceMapper.toDTO(getEntity(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceDTO> findAll() {
        return maintenanceMapper.toDTOList(maintenanceRepository.findAll());
    }

    @Override
    @Transactional
    public MaintenanceDTO update(Long id, MaintenanceDTO dto) {
        validerDates(dto.getDateDebut(), dto.getDateFin());
        Maintenance maintenance = getEntity(id);
        maintenanceMapper.updateEntity(dto, maintenance);
        if (dto.getIdVehicule() != null) {
            maintenance.setVehicule(getVehicule(dto.getIdVehicule()));
        }
        return maintenanceMapper.toDTO(maintenanceRepository.save(maintenance));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }
        maintenanceRepository.deleteById(id);
    }

    @Override
    @Transactional
    public MaintenanceDTO affecterMaintenanceAVehicule(Long idVehicule, MaintenanceDTO maintenanceDTO) {
        Vehicule vehicule = getVehicule(idVehicule);
        validerDates(maintenanceDTO.getDateDebut(), maintenanceDTO.getDateFin());
        Maintenance maintenance;
        if (maintenanceDTO.getIdMaintenance() != null) {
            maintenance = getEntity(maintenanceDTO.getIdMaintenance());
            maintenanceMapper.updateEntity(maintenanceDTO, maintenance);
        } else {
            maintenance = maintenanceMapper.toEntity(maintenanceDTO);
        }
        maintenance.setVehicule(vehicule);
        return maintenanceMapper.toDTO(maintenanceRepository.save(maintenance));
    }

    private Maintenance getEntity(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
    }

    private Vehicule getVehicule(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    private void validerDates(LocalDate dateDebut, LocalDate dateFin) {
        if (dateFin.isBefore(dateDebut)) {
            throw new IllegalArgumentException("La date de fin ne peut pas précéder la date de début");
        }
    }
}
