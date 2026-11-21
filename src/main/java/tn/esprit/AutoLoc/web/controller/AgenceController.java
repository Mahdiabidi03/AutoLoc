package tn.esprit.AutoLoc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import tn.esprit.AutoLoc.service.IAgenceService;
import tn.esprit.AutoLoc.web.dto.AgenceDTO;

@Tag(name = "Agences", description = "Gestion des agences")
@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
public class AgenceController {

    private final IAgenceService agenceService;

    @Operation(summary = "Lister les agences")
    @ApiResponse(responseCode = "200", description = "Liste des agences")
    @GetMapping
    public List<AgenceDTO> findAll() {
        return agenceService.findAll();
    }

    @Operation(summary = "Consulter une agence")
    @ApiResponse(responseCode = "200", description = "Agence trouvée")
    @ApiResponse(responseCode = "404", description = "Agence introuvable")
    @GetMapping("/{id}")
    public AgenceDTO findById(@PathVariable Long id) {
        return agenceService.findById(id);
    }

    @Operation(summary = "Créer une agence")
    @ApiResponse(responseCode = "201", description = "Agence créée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgenceDTO create(@Valid @RequestBody AgenceDTO dto) {
        return agenceService.create(dto);
    }

    @Operation(summary = "Modifier une agence")
    @ApiResponse(responseCode = "200", description = "Agence modifiée")
    @ApiResponse(responseCode = "404", description = "Agence introuvable")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PutMapping("/{id}")
    public AgenceDTO update(@PathVariable Long id, @Valid @RequestBody AgenceDTO dto) {
        return agenceService.update(id, dto);
    }

    @Operation(summary = "Supprimer une agence")
    @ApiResponse(responseCode = "204", description = "Agence supprimée")
    @ApiResponse(responseCode = "404", description = "Agence introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        agenceService.deleteById(id);
    }
}
