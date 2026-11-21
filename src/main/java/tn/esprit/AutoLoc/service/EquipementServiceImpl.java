package tn.esprit.AutoLoc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.AutoLoc.domain.Equipement;
import tn.esprit.AutoLoc.exception.ResourceNotFoundException;
import tn.esprit.AutoLoc.mapper.EquipementMapper;
import tn.esprit.AutoLoc.repository.IEquipementRepository;
import tn.esprit.AutoLoc.web.dto.EquipementDTO;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;
    private final EquipementMapper equipementMapper;

    @Override
    @Transactional
    public EquipementDTO create(EquipementDTO dto) {
        if (dto.getIdEquipement() != null) {
            throw new IllegalArgumentException("Un nouvel équipement ne doit pas avoir d'identifiant");
        }
        return equipementMapper.toDTO(equipementRepository.save(equipementMapper.toEntity(dto)));
    }

    @Override
    @Transactional(readOnly = true)
    public EquipementDTO findById(Long id) {
        return equipementMapper.toDTO(getEntity(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipementDTO> findAll() {
        return equipementMapper.toDTOList(equipementRepository.findAll());
    }

    @Override
    @Transactional
    public EquipementDTO update(Long id, EquipementDTO dto) {
        Equipement equipement = getEntity(id);
        equipementMapper.updateEntity(dto, equipement);
        return equipementMapper.toDTO(equipementRepository.save(equipement));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!equipementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Equipement", id);
        }
        equipementRepository.deleteById(id);
    }

    private Equipement getEntity(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
    }
}
