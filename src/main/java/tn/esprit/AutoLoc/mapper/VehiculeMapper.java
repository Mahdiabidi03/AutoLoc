package tn.esprit.AutoLoc.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tn.esprit.AutoLoc.domain.Equipement;
import tn.esprit.AutoLoc.domain.Vehicule;
import tn.esprit.AutoLoc.web.dto.VehiculeDTO;

@Mapper(componentModel = "spring")
public interface VehiculeMapper {

    @Mapping(source = "agence.idAgence", target = "idAgence")
    @Mapping(source = "equipements", target = "equipementIds")
    VehiculeDTO toDTO(Vehicule entity);

    List<VehiculeDTO> toDTOList(List<Vehicule> entities);

    @Mapping(target = "idVehicule", ignore = true)
    @Mapping(target = "agence", ignore = true)
    @Mapping(target = "equipements", ignore = true)
    @Mapping(target = "maintenances", ignore = true)
    Vehicule toEntity(VehiculeDTO dto);

    @Mapping(target = "idVehicule", ignore = true)
    @Mapping(target = "agence", ignore = true)
    @Mapping(target = "equipements", ignore = true)
    @Mapping(target = "maintenances", ignore = true)
    void updateEntity(VehiculeDTO dto, @MappingTarget Vehicule entity);

    default List<Long> mapEquipements(Set<Equipement> equipements) {
        return equipements == null ? null : equipements.stream()
                .map(Equipement::getIdEquipement)
                .collect(Collectors.toList());
    }
}
