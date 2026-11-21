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
import tn.esprit.AutoLoc.service.IMaintenanceService;
import tn.esprit.AutoLoc.web.dto.MaintenanceDTO;

@Tag(name = "Maintenances", description = "Gestion des maintenances des véhicules")
@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    @Operation(summary = "Lister les maintenances")
    @ApiResponse(responseCode = "200", description = "Liste des maintenances")
    @GetMapping
    public List<MaintenanceDTO> findAll() {
        return maintenanceService.findAll();
    }

    @Operation(summary = "Consulter une maintenance")
    @ApiResponse(responseCode = "200", description = "Maintenance trouvée")
    @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    @GetMapping("/{id}")
    public MaintenanceDTO findById(@PathVariable Long id) {
        return maintenanceService.findById(id);
    }

    @Operation(summary = "Créer une maintenance")
    @ApiResponse(responseCode = "201", description = "Maintenance créée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceDTO create(@Valid @RequestBody MaintenanceDTO dto) {
        return maintenanceService.create(dto);
    }

    @Operation(summary = "Modifier une maintenance")
    @ApiResponse(responseCode = "200", description = "Maintenance modifiée")
    @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PutMapping("/{id}")
    public MaintenanceDTO update(@PathVariable Long id, @Valid @RequestBody MaintenanceDTO dto) {
        return maintenanceService.update(id, dto);
    }

    @Operation(summary = "Affecter une maintenance à un véhicule")
    @ApiResponse(responseCode = "200", description = "Maintenance affectée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Maintenance ou véhicule introuvable")
    @PostMapping("/vehicule/{idVehicule}")
    public MaintenanceDTO affecterMaintenanceAVehicule(
            @PathVariable Long idVehicule,
            @Valid @RequestBody MaintenanceDTO maintenanceDTO) {
        return maintenanceService.affecterMaintenanceAVehicule(idVehicule, maintenanceDTO);
    }

    @Operation(summary = "Supprimer une maintenance")
    @ApiResponse(responseCode = "204", description = "Maintenance supprimée")
    @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        maintenanceService.deleteById(id);
    }
}
