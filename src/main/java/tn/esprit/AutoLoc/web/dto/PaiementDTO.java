package tn.esprit.AutoLoc.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.AutoLoc.domain.ModePaiement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaiementDTO {

    private Long idPaiement;

    @NotNull(message = "Le montant est obligatoire")
    @Positive(message = "Le montant doit être strictement positif")
    private BigDecimal montant;

    @NotNull(message = "La date de paiement est obligatoire")
    private LocalDate datePaiement;

    @NotNull(message = "Le mode de paiement est obligatoire")
    private ModePaiement modePaiement;

    @Positive(message = "L'identifiant du contrat doit être strictement positif")
    private Long idContrat;
}
