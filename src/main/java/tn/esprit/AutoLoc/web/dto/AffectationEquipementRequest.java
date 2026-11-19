package tn.esprit.AutoLoc.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AffectationEquipementRequest(

        @Schema(description = "Identifiant du véhicule", example = "1")
        @NotNull(message = "L'identifiant du véhicule est obligatoire")
        @Positive(message = "L'identifiant du véhicule doit être strictement positif")
        Long idVehicule,

        @Schema(description = "Identifiant de l'équipement à affecter", example = "1")
        @NotNull(message = "L'identifiant de l'équipement est obligatoire")
        @Positive(message = "L'identifiant de l'équipement doit être strictement positif")
        Long idEquipement) {
}