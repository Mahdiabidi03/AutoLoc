package tn.esprit.AutoLoc.web.dto;

import tn.esprit.AutoLoc.domain.Equipement;

public record EquipementResponse(Long idEquipement, String libelle) {

    public static EquipementResponse from(Equipement equipement) {
        return new EquipementResponse(equipement.getIdEquipement(), equipement.getLibelle());
    }
}