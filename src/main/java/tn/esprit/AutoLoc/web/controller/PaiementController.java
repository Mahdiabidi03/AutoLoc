package tn.esprit.AutoLoc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.AutoLoc.service.IPaiementService;
import tn.esprit.AutoLoc.web.dto.PaiementDTO;

@Tag(name = "Paiements", description = "Consultation des paiements (lecture seule, gérés via le contrat)")
@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
public class PaiementController {

    private final IPaiementService paiementService;

    @Operation(summary = "Lister les paiements")
    @ApiResponse(responseCode = "200", description = "Liste des paiements")
    @GetMapping
    public List<PaiementDTO> findAll() {
        return paiementService.findAll();
    }

    @Operation(summary = "Consulter un paiement")
    @ApiResponse(responseCode = "200", description = "Paiement trouvé")
    @ApiResponse(responseCode = "404", description = "Paiement introuvable")
    @GetMapping("/{id}")
    public PaiementDTO findById(@PathVariable Long id) {
        return paiementService.findById(id);
    }
}
