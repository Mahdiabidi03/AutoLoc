package tn.esprit.AutoLoc.service;

import java.util.List;
import tn.esprit.AutoLoc.web.dto.EmployeDTO;

public interface IEmployeService {
    EmployeDTO create(EmployeDTO dto);
    EmployeDTO findById(Long id);
    List<EmployeDTO> findAll();
    EmployeDTO update(Long id, EmployeDTO dto);
    void deleteById(Long id);
}
