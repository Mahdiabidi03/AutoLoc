package tn.esprit.AutoLoc.web.dto;

import java.math.BigDecimal;
import java.util.List;
import tn.esprit.AutoLoc.domain.CategorieVehicule;
import tn.esprit.AutoLoc.domain.StatutVehicule;
import tn.esprit.AutoLoc.domain.Vehicule;

public record VehiculeResponse(
        Long idVehicule,
        String immatriculation,
        String marque,
        String modele,
        CategorieVehicule categorie,
        BigDecimal tarifJournalier,
        StatutVehicule statut,
        Long idAgence,
        List<EquipementResponse> equipements) {

    public static VehiculeResponse from(Vehicule vehicule) {
        return new VehiculeResponse(
                vehicule.getIdVehicule(),
                vehicule.getImmatriculation(),
                vehicule.getMarque(),
                vehicule.getModele(),
                vehicule.getCategorie(),
                vehicule.getTarifJournalier(),
                vehicule.getStatut(),
                vehicule.getAgence() != null ? vehicule.getAgence().getIdAgence() : null,
                vehicule.getEquipements().stream().map(EquipementResponse::from).toList());
    }
}