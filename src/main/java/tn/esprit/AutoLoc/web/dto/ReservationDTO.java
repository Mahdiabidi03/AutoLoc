package tn.esprit.AutoLoc.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.AutoLoc.domain.StatutReservation;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private Long idReservation;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate dateFin;

    @NotNull(message = "Le statut est obligatoire")
    private StatutReservation statut;

    @NotNull(message = "L'identifiant du client est obligatoire")
    @Positive(message = "L'identifiant du client doit être strictement positif")
    private Long idClient;

    @NotNull(message = "L'identifiant du véhicule est obligatoire")
    @Positive(message = "L'identifiant du véhicule doit être strictement positif")
    private Long idVehicule;
}
