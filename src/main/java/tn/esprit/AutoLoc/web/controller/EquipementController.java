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
import tn.esprit.AutoLoc.service.IEquipementService;
import tn.esprit.AutoLoc.web.dto.EquipementDTO;

@Tag(name = "Équipements", description = "Gestion des équipements des véhicules")
@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
public class EquipementController {

    private final IEquipementService equipementService;

    @Operation(summary = "Lister les équipements")
    @ApiResponse(responseCode = "200", description = "Liste des équipements")
    @GetMapping
    public List<EquipementDTO> findAll() {
        return equipementService.findAll();
    }

    @Operation(summary = "Consulter un équipement")
    @ApiResponse(responseCode = "200", description = "Équipement trouvé")
    @ApiResponse(responseCode = "404", description = "Équipement introuvable")
    @GetMapping("/{id}")
    public EquipementDTO findById(@PathVariable Long id) {
        return equipementService.findById(id);
    }

    @Operation(summary = "Créer un équipement")
    @ApiResponse(responseCode = "201", description = "Équipement créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipementDTO create(@Valid @RequestBody EquipementDTO dto) {
        return equipementService.create(dto);
    }

    @Operation(summary = "Modifier un équipement")
    @ApiResponse(responseCode = "200", description = "Équipement modifié")
    @ApiResponse(responseCode = "404", description = "Équipement introuvable")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PutMapping("/{id}")
    public EquipementDTO update(@PathVariable Long id, @Valid @RequestBody EquipementDTO dto) {
        return equipementService.update(id, dto);
    }

    @Operation(summary = "Supprimer un équipement")
    @ApiResponse(responseCode = "204", description = "Équipement supprimé")
    @ApiResponse(responseCode = "404", description = "Équipement introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        equipementService.deleteById(id);
    }
}
