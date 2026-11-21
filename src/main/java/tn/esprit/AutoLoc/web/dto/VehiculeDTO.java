package tn.esprit.AutoLoc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.AutoLoc.domain.CategorieVehicule;
import tn.esprit.AutoLoc.domain.StatutVehicule;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehiculeDTO {

    private Long idVehicule;

    @NotBlank(message = "L'immatriculation est obligatoire")
    private String immatriculation;

    @NotBlank(message = "La marque est obligatoire")
    private String marque;

    @NotBlank(message = "Le modèle est obligatoire")
    private String modele;

    @NotNull(message = "La catégorie est obligatoire")
    private CategorieVehicule categorie;

    @NotNull(message = "Le tarif journalier est obligatoire")
    @PositiveOrZero(message = "Le tarif journalier doit être positif ou nul")
    private BigDecimal tarifJournalier;

    @NotNull(message = "Le statut est obligatoire")
    private StatutVehicule statut;

    @Positive(message = "L'identifiant de l'agence doit être strictement positif")
    private Long idAgence;

    private List<Long> equipementIds;
}
