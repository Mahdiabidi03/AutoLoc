package tn.esprit.AutoLoc.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.AutoLoc.domain.Paiement;
import tn.esprit.AutoLoc.web.dto.PaiementDTO;

@Mapper(componentModel = "spring")
public interface PaiementMapper {

    @Mapping(source = "contrat.idContrat", target = "idContrat")
    PaiementDTO toDTO(Paiement entity);

    List<PaiementDTO> toDTOList(List<Paiement> entities);

    @Mapping(target = "idPaiement", ignore = true)
    @Mapping(target = "contrat", ignore = true)
    Paiement toEntity(PaiementDTO dto);
}
