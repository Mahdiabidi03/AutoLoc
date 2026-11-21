package tn.esprit.AutoLoc.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tn.esprit.AutoLoc.domain.Contrat;
import tn.esprit.AutoLoc.web.dto.ContratDTO;

@Mapper(componentModel = "spring", uses = PaiementMapper.class)
public interface ContratMapper {

    @Mapping(source = "reservation.idReservation", target = "idReservation")
    ContratDTO toDTO(Contrat entity);

    List<ContratDTO> toDTOList(List<Contrat> entities);

    @Mapping(target = "idContrat", ignore = true)
    @Mapping(target = "reservation", ignore = true)
    @Mapping(target = "paiements", ignore = true)
    Contrat toEntity(ContratDTO dto);

    @Mapping(target = "idContrat", ignore = true)
    @Mapping(target = "reservation", ignore = true)
    @Mapping(target = "paiements", ignore = true)
    void updateEntity(ContratDTO dto, @MappingTarget Contrat entity);
}
