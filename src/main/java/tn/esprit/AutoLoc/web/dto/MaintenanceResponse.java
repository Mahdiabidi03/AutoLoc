package tn.esprit.AutoLoc.web.dto;

import java.time.LocalDate;
import tn.esprit.AutoLoc.domain.Maintenance;
import tn.esprit.AutoLoc.domain.Vehicule;

public record MaintenanceResponse(
        Long idMaintenance,
        LocalDate dateDebut,
        LocalDate dateFin,
        String description,
        Long idVehicule,
        String immatriculation) {

    public static MaintenanceResponse from(Maintenance maintenance) {
        Vehicule vehicule = maintenance.getVehicule();
        return new MaintenanceResponse(
                maintenance.getIdMaintenance(),
                maintenance.getDateDebut(),
                maintenance.getDateFin(),
                maintenance.getDescription(),
                vehicule != null ? vehicule.getIdVehicule() : null,
                vehicule != null ? vehicule.getImmatriculation() : null);
    }
}