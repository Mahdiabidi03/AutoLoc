package tn.esprit.AutoLoc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.AutoLoc.domain.Agence;
import tn.esprit.AutoLoc.domain.Equipement;
import tn.esprit.AutoLoc.domain.Vehicule;
import tn.esprit.AutoLoc.exception.ResourceNotFoundException;
import tn.esprit.AutoLoc.mapper.EquipementMapper;
import tn.esprit.AutoLoc.mapper.VehiculeMapper;
import tn.esprit.AutoLoc.repository.IAgenceRepository;
import tn.esprit.AutoLoc.repository.IEquipementRepository;
import tn.esprit.AutoLoc.repository.IVehiculeRepository;
import tn.esprit.AutoLoc.web.dto.EquipementDTO;
import tn.esprit.AutoLoc.web.dto.VehiculeDTO;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;
    private final EquipementMapper equipementMapper;
    private final VehiculeMapper vehiculeMapper;

    @Override
    @Transactional
    public VehiculeDTO create(VehiculeDTO dto) {
        if (dto.getIdVehicule() != null) {
            throw new IllegalArgumentException("Un nouveau véhicule ne doit pas avoir d'identifiant");
        }
        Vehicule vehicule = vehiculeMapper.toEntity(dto);
        if (dto.getIdAgence() != null) {
            vehicule.setAgence(getAgence(dto.getIdAgence()));
        }
        return vehiculeMapper.toDTO(vehiculeRepository.save(vehicule));
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculeDTO findById(Long id) {
        return vehiculeMapper.toDTO(getEntity(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> findAll() {
        return vehiculeMapper.toDTOList(vehiculeRepository.findAll());
    }

    @Override
    @Transactional
    public VehiculeDTO update(Long id, VehiculeDTO dto) {
        Vehicule vehicule = getEntity(id);
        vehiculeMapper.updateEntity(dto, vehicule);
        if (dto.getIdAgence() != null) {
            vehicule.setAgence(getAgence(dto.getIdAgence()));
        }
        return vehiculeMapper.toDTO(vehiculeRepository.save(vehicule));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public VehiculeDTO affecterAAgence(Long idVehicule, Long idAgence) {
        Vehicule vehicule = getEntity(idVehicule);
        vehicule.setAgence(getAgence(idAgence));
        return vehiculeMapper.toDTO(vehiculeRepository.save(vehicule));
    }

    @Override
    @Transactional
    public VehiculeDTO affecterEquipementAVehicule(Long idVehicule, EquipementDTO equipementDTO) {
        Vehicule vehicule = getEntity(idVehicule);
        Equipement equipement;
        if (equipementDTO.getIdEquipement() != null) {
            equipement = equipementRepository.findById(equipementDTO.getIdEquipement())
                    .orElseThrow(() -> new ResourceNotFoundException("Equipement", equipementDTO.getIdEquipement()));
        } else {
            equipement = equipementRepository.save(equipementMapper.toEntity(equipementDTO));
        }
        vehicule.getEquipements().add(equipement);
        return vehiculeMapper.toDTO(vehiculeRepository.save(vehicule));
    }

    private Vehicule getEntity(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    private Agence getAgence(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }
}
