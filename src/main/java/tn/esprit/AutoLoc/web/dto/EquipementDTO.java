package tn.esprit.AutoLoc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquipementDTO {

    @Positive(message = "L'identifiant de l'équipement doit être strictement positif")
    private Long idEquipement;

    @NotBlank(message = "Le libellé est obligatoire")
    private String libelle;
}
