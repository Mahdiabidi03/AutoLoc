package tn.esprit.AutoLoc.service;

import java.util.List;
import tn.esprit.AutoLoc.web.dto.AgenceDTO;

public interface IAgenceService {
    AgenceDTO create(AgenceDTO dto);
    AgenceDTO findById(Long id);
    List<AgenceDTO> findAll();
    AgenceDTO update(Long id, AgenceDTO dto);
    void deleteById(Long id);
}
