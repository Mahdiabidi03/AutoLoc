package tn.esprit.AutoLoc.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tn.esprit.AutoLoc.domain.Employe;
import tn.esprit.AutoLoc.web.dto.EmployeDTO;

@Mapper(componentModel = "spring")
public interface EmployeMapper {

    @Mapping(source = "agence.idAgence", target = "idAgence")
    EmployeDTO toDTO(Employe entity);

    List<EmployeDTO> toDTOList(List<Employe> entities);

    @Mapping(target = "idEmploye", ignore = true)
    @Mapping(target = "agence", ignore = true)
    Employe toEntity(EmployeDTO dto);

    @Mapping(target = "idEmploye", ignore = true)
    @Mapping(target = "agence", ignore = true)
    void updateEntity(EmployeDTO dto, @MappingTarget Employe entity);
}
