package tn.esprit.AutoLoc.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AffectationMaintenanceRequest(

        @Schema(description = "Identifiant de la maintenance à affecter", example = "1")
        @NotNull(message = "L'identifiant de la maintenance est obligatoire")
        @Positive(message = "L'identifiant de la maintenance doit être strictement positif")
        Long idMaintenance,

        @Schema(description = "Identifiant du véhicule concerné", example = "1")
        @NotNull(message = "L'identifiant du véhicule est obligatoire")
        @Positive(message = "L'identifiant du véhicule doit être strictement positif")
        Long idVehicule) {
}