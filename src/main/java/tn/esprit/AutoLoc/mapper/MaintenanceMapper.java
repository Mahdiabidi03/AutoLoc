package tn.esprit.AutoLoc.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tn.esprit.AutoLoc.domain.Maintenance;
import tn.esprit.AutoLoc.web.dto.MaintenanceDTO;

@Mapper(componentModel = "spring")
public interface MaintenanceMapper {

    @Mapping(source = "vehicule.idVehicule", target = "idVehicule")
    MaintenanceDTO toDTO(Maintenance entity);

    List<MaintenanceDTO> toDTOList(List<Maintenance> entities);

    @Mapping(target = "idMaintenance", ignore = true)
    @Mapping(target = "vehicule", ignore = true)
    Maintenance toEntity(MaintenanceDTO dto);

    @Mapping(target = "idMaintenance", ignore = true)
    @Mapping(target = "vehicule", ignore = true)
    void updateEntity(MaintenanceDTO dto, @MappingTarget Maintenance maintenance);
}
