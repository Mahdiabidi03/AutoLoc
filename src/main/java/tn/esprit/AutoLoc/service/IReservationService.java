package tn.esprit.AutoLoc.service;

import java.util.List;
import tn.esprit.AutoLoc.web.dto.ReservationDTO;

public interface IReservationService {
    ReservationDTO create(ReservationDTO dto);
    ReservationDTO findById(Long id);
    List<ReservationDTO> findAll();
    ReservationDTO update(Long id, ReservationDTO dto);
    void deleteById(Long id);
}
