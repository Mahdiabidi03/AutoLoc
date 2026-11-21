package tn.esprit.AutoLoc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.AutoLoc.domain.Paiement;
import tn.esprit.AutoLoc.exception.ResourceNotFoundException;
import tn.esprit.AutoLoc.mapper.PaiementMapper;
import tn.esprit.AutoLoc.repository.IPaiementRepository;
import tn.esprit.AutoLoc.web.dto.PaiementDTO;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;
    private final PaiementMapper paiementMapper;

    @Override
    @Transactional(readOnly = true)
    public PaiementDTO findById(Long id) {
        return paiementMapper.toDTO(getEntity(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaiementDTO> findAll() {
        return paiementMapper.toDTOList(paiementRepository.findAll());
    }

    private Paiement getEntity(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
    }
}
