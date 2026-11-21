package tn.esprit.AutoLoc.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tn.esprit.AutoLoc.domain.Reservation;
import tn.esprit.AutoLoc.web.dto.ReservationDTO;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(source = "client.idClient", target = "idClient")
    @Mapping(source = "vehicule.idVehicule", target = "idVehicule")
    ReservationDTO toDTO(Reservation entity);

    List<ReservationDTO> toDTOList(List<Reservation> entities);

    @Mapping(target = "idReservation", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "vehicule", ignore = true)
    @Mapping(target = "contrat", ignore = true)
    Reservation toEntity(ReservationDTO dto);

    @Mapping(target = "idReservation", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "vehicule", ignore = true)
    @Mapping(target = "contrat", ignore = true)
    void updateEntity(ReservationDTO dto, @MappingTarget Reservation entity);
}
