package tn.esprit.AutoLoc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.AutoLoc.domain.Agence;
import tn.esprit.AutoLoc.domain.Employe;
import tn.esprit.AutoLoc.exception.ResourceNotFoundException;
import tn.esprit.AutoLoc.mapper.EmployeMapper;
import tn.esprit.AutoLoc.repository.IAgenceRepository;
import tn.esprit.AutoLoc.repository.IEmployeRepository;
import tn.esprit.AutoLoc.web.dto.EmployeDTO;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;
    private final IAgenceRepository agenceRepository;
    private final EmployeMapper employeMapper;

    @Override
    @Transactional
    public EmployeDTO create(EmployeDTO dto) {
        if (dto.getIdEmploye() != null) {
            throw new IllegalArgumentException("Un nouvel employé ne doit pas avoir d'identifiant");
        }
        Employe employe = employeMapper.toEntity(dto);
        if (dto.getIdAgence() != null) {
            employe.setAgence(getAgence(dto.getIdAgence()));
        }
        return employeMapper.toDTO(employeRepository.save(employe));
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeDTO findById(Long id) {
        return employeMapper.toDTO(getEntity(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeDTO> findAll() {
        return employeMapper.toDTOList(employeRepository.findAll());
    }

    @Override
    @Transactional
    public EmployeDTO update(Long id, EmployeDTO dto) {
        Employe employe = getEntity(id);
        employeMapper.updateEntity(dto, employe);
        if (dto.getIdAgence() != null) {
            employe.setAgence(getAgence(dto.getIdAgence()));
        }
        return employeMapper.toDTO(employeRepository.save(employe));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employe", id);
        }
        employeRepository.deleteById(id);
    }

    private Employe getEntity(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));
    }

    private Agence getAgence(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }
}
