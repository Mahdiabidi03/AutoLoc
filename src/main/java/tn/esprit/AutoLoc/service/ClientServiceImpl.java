package tn.esprit.AutoLoc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.AutoLoc.domain.Client;
import tn.esprit.AutoLoc.exception.ResourceNotFoundException;
import tn.esprit.AutoLoc.mapper.ClientMapper;
import tn.esprit.AutoLoc.repository.IClientRepository;
import tn.esprit.AutoLoc.web.dto.ClientDTO;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    @Transactional
    public ClientDTO create(ClientDTO dto) {
        if (dto.getIdClient() != null) {
            throw new IllegalArgumentException("Un nouveau client ne doit pas avoir d'identifiant");
        }
        return clientMapper.toDTO(clientRepository.save(clientMapper.toEntity(dto)));
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        return clientMapper.toDTO(getEntity(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientDTO> findAll() {
        return clientMapper.toDTOList(clientRepository.findAll());
    }

    @Override
    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        Client client = getEntity(id);
        clientMapper.updateEntity(dto, client);
        return clientMapper.toDTO(clientRepository.save(client));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client", id);
        }
        clientRepository.deleteById(id);
    }

    private Client getEntity(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }
}
