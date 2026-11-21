package tn.esprit.AutoLoc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.AutoLoc.service.IContratService;
import tn.esprit.AutoLoc.web.dto.ContratDTO;

@Tag(name = "Contrats", description = "Gestion des contrats de location")
@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
public class ContratController {

    private final IContratService contratService;

    @Operation(summary = "Lister les contrats")
    @ApiResponse(responseCode = "200", description = "Liste des contrats")
    @GetMapping
    public List<ContratDTO> findAll() {
        return contratService.findAll();
    }

    @Operation(summary = "Consulter un contrat")
    @ApiResponse(responseCode = "200", description = "Contrat trouvé")
    @ApiResponse(responseCode = "404", description = "Contrat introuvable")
    @GetMapping("/{id}")
    public ContratDTO findById(@Parameter(description = "Identifiant du contrat") @PathVariable Long id) {
        return contratService.findById(id);
    }

    @Operation(summary = "Créer un contrat (avec ses paiements)")
    @ApiResponse(responseCode = "201", description = "Contrat créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContratDTO create(@Valid @RequestBody ContratDTO dto) {
        return contratService.create(dto);
    }

    @Operation(summary = "Modifier un contrat")
    @ApiResponse(responseCode = "200", description = "Contrat modifié")
    @ApiResponse(responseCode = "404", description = "Contrat introuvable")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PutMapping("/{id}")
    public ContratDTO update(@PathVariable Long id, @Valid @RequestBody ContratDTO dto) {
        return contratService.update(id, dto);
    }

    @Operation(summary = "Supprimer un contrat (et ses paiements)")
    @ApiResponse(responseCode = "204", description = "Contrat supprimé")
    @ApiResponse(responseCode = "404", description = "Contrat introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        contratService.deleteById(id);
    }
}
