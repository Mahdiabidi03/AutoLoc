package tn.esprit.AutoLoc.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tn.esprit.AutoLoc.domain.Agence;
import tn.esprit.AutoLoc.web.dto.AgenceDTO;

@Mapper(componentModel = "spring")
public interface AgenceMapper {

    AgenceDTO toDTO(Agence entity);

    List<AgenceDTO> toDTOList(List<Agence> entities);

    @Mapping(target = "idAgence", ignore = true)
    @Mapping(target = "vehicules", ignore = true)
    @Mapping(target = "employes", ignore = true)
    Agence toEntity(AgenceDTO dto);

    @Mapping(target = "idAgence", ignore = true)
    @Mapping(target = "vehicules", ignore = true)
    @Mapping(target = "employes", ignore = true)
    void updateEntity(AgenceDTO dto, @MappingTarget Agence entity);
}
