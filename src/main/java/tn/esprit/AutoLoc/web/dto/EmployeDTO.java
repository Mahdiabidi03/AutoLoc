package tn.esprit.AutoLoc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.AutoLoc.domain.RoleEmploye;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeDTO {

    private Long idEmploye;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotNull(message = "Le rôle est obligatoire")
    private RoleEmploye role;

    @Positive(message = "L'identifiant de l'agence doit être strictement positif")
    private Long idAgence;
}
