package tn.esprit.AutoLoc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgenceDTO {

    private Long idAgence;

    @NotBlank(message = "Le nom de l'agence est obligatoire")
    private String nom;

    @NotBlank(message = "La ville est obligatoire")
    private String ville;

    private String adresse;

    @Pattern(regexp = "^\\+?[0-9 ]{8,15}$", message = "Le téléphone doit contenir de 8 à 15 chiffres")
    private String telephone;
}
