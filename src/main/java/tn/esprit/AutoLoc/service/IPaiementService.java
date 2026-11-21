package tn.esprit.AutoLoc.service;

import java.util.List;
import tn.esprit.AutoLoc.web.dto.PaiementDTO;

public interface IPaiementService {
    PaiementDTO findById(Long id);
    List<PaiementDTO> findAll();
}
