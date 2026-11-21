package tn.esprit.AutoLoc.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.AutoLoc.domain.Client;
import tn.esprit.AutoLoc.domain.Reservation;
import tn.esprit.AutoLoc.domain.Vehicule;
import tn.esprit.AutoLoc.exception.ResourceNotFoundException;
import tn.esprit.AutoLoc.mapper.ReservationMapper;
import tn.esprit.AutoLoc.repository.IClientRepository;
import tn.esprit.AutoLoc.repository.IReservationRepository;
import tn.esprit.AutoLoc.repository.IVehiculeRepository;
import tn.esprit.AutoLoc.web.dto.ReservationDTO;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final ReservationMapper reservationMapper;

    @Override
    @Transactional
    public ReservationDTO create(ReservationDTO dto) {
        if (dto.getIdReservation() != null) {
            throw new IllegalArgumentException("Une nouvelle réservation ne doit pas avoir d'identifiant");
        }
        validerDates(dto.getDateDebut(), dto.getDateFin());
        Reservation reservation = reservationMapper.toEntity(dto);
        if (dto.getIdClient() != null) {
            reservation.setClient(getClient(dto.getIdClient()));
        }
        if (dto.getIdVehicule() != null) {
            reservation.setVehicule(getVehicule(dto.getIdVehicule()));
        }
        return reservationMapper.toDTO(reservationRepository.save(reservation));
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationDTO findById(Long id) {
        return reservationMapper.toDTO(getEntity(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationDTO> findAll() {
        return reservationMapper.toDTOList(reservationRepository.findAll());
    }

    @Override
    @Transactional
    public ReservationDTO update(Long id, ReservationDTO dto) {
        validerDates(dto.getDateDebut(), dto.getDateFin());
        Reservation reservation = getEntity(id);
        reservationMapper.updateEntity(dto, reservation);
        if (dto.getIdClient() != null) {
            reservation.setClient(getClient(dto.getIdClient()));
        }
        if (dto.getIdVehicule() != null) {
            reservation.setVehicule(getVehicule(dto.getIdVehicule()));
        }
        return reservationMapper.toDTO(reservationRepository.save(reservation));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }
        reservationRepository.deleteById(id);
    }

    private Reservation getEntity(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
    }

    private Client getClient(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
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
