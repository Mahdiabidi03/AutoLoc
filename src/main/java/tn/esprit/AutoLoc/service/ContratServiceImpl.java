package tn.esprit.AutoLoc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.AutoLoc.domain.Contrat;
import tn.esprit.AutoLoc.domain.Paiement;
import tn.esprit.AutoLoc.domain.Reservation;
import tn.esprit.AutoLoc.exception.ResourceNotFoundException;
import tn.esprit.AutoLoc.mapper.ContratMapper;
import tn.esprit.AutoLoc.mapper.PaiementMapper;
import tn.esprit.AutoLoc.repository.IContratRepository;
import tn.esprit.AutoLoc.repository.IReservationRepository;
import tn.esprit.AutoLoc.web.dto.ContratDTO;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final IReservationRepository reservationRepository;
    private final ContratMapper contratMapper;
    private final PaiementMapper paiementMapper;

    @Override
    @Transactional
    public ContratDTO create(ContratDTO dto) {
        if (dto.getIdContrat() != null) {
            throw new IllegalArgumentException("Un nouveau contrat ne doit pas avoir d'identifiant");
        }
        Contrat contrat = contratMapper.toEntity(dto);
        if (dto.getIdReservation() != null) {
            contrat.setReservation(getReservation(dto.getIdReservation()));
        }
        if (dto.getPaiements() != null) {
            for (var paiementDTO : dto.getPaiements()) {
                Paiement paiement = paiementMapper.toEntity(paiementDTO);
                paiement.setContrat(contrat);
                contrat.getPaiements().add(paiement);
            }
        }
        return contratMapper.toDTO(contratRepository.save(contrat));
    }

    @Override
    @Transactional(readOnly = true)
    public ContratDTO findById(Long id) {
        return contratMapper.toDTO(getEntity(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContratDTO> findAll() {
        return contratMapper.toDTOList(contratRepository.findAll());
    }

    @Override
    @Transactional
    public ContratDTO update(Long id, ContratDTO dto) {
        Contrat contrat = getEntity(id);
        contratMapper.updateEntity(dto, contrat);
        if (dto.getIdReservation() != null) {
            contrat.setReservation(getReservation(dto.getIdReservation()));
        }
        return contratMapper.toDTO(contratRepository.save(contrat));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        contratRepository.deleteById(id);
    }

    private Contrat getEntity(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }

    private Reservation getReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
    }
}
