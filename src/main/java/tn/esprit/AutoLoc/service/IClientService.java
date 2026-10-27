package tn.esprit.AutoLoc.service;

import java.util.List;
import tn.esprit.AutoLoc.domain.Client;

public interface IClientService {
    Client create(Client client);
    Client findById(Long id);
    List<Client> findAll();
    Client update(Long id, Client client);
    void deleteById(Long id);
}