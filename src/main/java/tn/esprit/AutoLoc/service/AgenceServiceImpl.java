package tn.esprit.AutoLoc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.AutoLoc.domain.Agence;
import tn.esprit.AutoLoc.exception.ResourceNotFoundException;
import tn.esprit.AutoLoc.mapper.AgenceMapper;
import tn.esprit.AutoLoc.repository.IAgenceRepository;
import tn.esprit.AutoLoc.web.dto.AgenceDTO;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;
    private final AgenceMapper agenceMapper;

    @Override
    @Transactional
    public AgenceDTO create(AgenceDTO dto) {
        if (dto.getIdAgence() != null) {
            throw new IllegalArgumentException("Une nouvelle agence ne doit pas avoir d'identifiant");
        }
        return agenceMapper.toDTO(agenceRepository.save(agenceMapper.toEntity(dto)));
    }

    @Override
    @Transactional(readOnly = true)
    public AgenceDTO findById(Long id) {
        return agenceMapper.toDTO(getEntity(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgenceDTO> findAll() {
        return agenceMapper.toDTOList(agenceRepository.findAll());
    }

    @Override
    @Transactional
    public AgenceDTO update(Long id, AgenceDTO dto) {
        Agence agence = getEntity(id);
        agenceMapper.updateEntity(dto, agence);
        return agenceMapper.toDTO(agenceRepository.save(agence));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", id);
        }
        agenceRepository.deleteById(id);
    }

    private Agence getEntity(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }
}
