package tn.esprit.AutoLoc.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tn.esprit.AutoLoc.domain.Equipement;
import tn.esprit.AutoLoc.web.dto.EquipementDTO;

@Mapper(componentModel = "spring")
public interface EquipementMapper {

    EquipementDTO toDTO(Equipement entity);

    List<EquipementDTO> toDTOList(List<Equipement> entities);

    @Mapping(target = "idEquipement", ignore = true)
    @Mapping(target = "vehicules", ignore = true)
    Equipement toEntity(EquipementDTO dto);

    @Mapping(target = "idEquipement", ignore = true)
    @Mapping(target = "vehicules", ignore = true)
    void updateEntity(EquipementDTO dto, @MappingTarget Equipement entity);
}
