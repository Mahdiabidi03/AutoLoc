package tn.esprit.AutoLoc.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tn.esprit.AutoLoc.domain.Client;
import tn.esprit.AutoLoc.web.dto.ClientDTO;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDTO toDTO(Client entity);

    List<ClientDTO> toDTOList(List<Client> entities);

    @Mapping(target = "idClient", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    Client toEntity(ClientDTO dto);

    @Mapping(target = "idClient", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    void updateEntity(ClientDTO dto, @MappingTarget Client entity);
}
