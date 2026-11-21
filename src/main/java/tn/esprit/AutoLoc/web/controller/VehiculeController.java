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
import tn.esprit.AutoLoc.service.IVehiculeService;
import tn.esprit.AutoLoc.web.dto.EquipementDTO;
import tn.esprit.AutoLoc.web.dto.VehiculeDTO;

@Tag(name = "Véhicules", description = "Gestion des véhicules")
@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @Operation(summary = "Lister les véhicules")
    @ApiResponse(responseCode = "200", description = "Liste des véhicules")
    @GetMapping
    public List<VehiculeDTO> findAll() {
        return vehiculeService.findAll();
    }

    @Operation(summary = "Consulter un véhicule")
    @ApiResponse(responseCode = "200", description = "Véhicule trouvé")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    @GetMapping("/{id}")
    public VehiculeDTO findById(@PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    @Operation(summary = "Créer un véhicule")
    @ApiResponse(responseCode = "201", description = "Véhicule créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeDTO create(@Valid @RequestBody VehiculeDTO dto) {
        return vehiculeService.create(dto);
    }

    @Operation(summary = "Modifier un véhicule")
    @ApiResponse(responseCode = "200", description = "Véhicule modifié")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PutMapping("/{id}")
    public VehiculeDTO update(@PathVariable Long id, @Valid @RequestBody VehiculeDTO dto) {
        return vehiculeService.update(id, dto);
    }

    @Operation(summary = "Affecter un véhicule à une agence")
    @ApiResponse(responseCode = "200", description = "Véhicule affecté")
    @ApiResponse(responseCode = "404", description = "Véhicule ou agence introuvable")
    @PutMapping("/{idVehicule}/agence/{idAgence}")
    public VehiculeDTO affecterAAgence(@PathVariable Long idVehicule, @PathVariable Long idAgence) {
        return vehiculeService.affecterAAgence(idVehicule, idAgence);
    }

    @Operation(summary = "Affecter un équipement à un véhicule")
    @ApiResponse(responseCode = "200", description = "Équipement affecté")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Véhicule ou équipement introuvable")
    @PostMapping("/{idVehicule}/equipements")
    public VehiculeDTO affecterEquipementAVehicule(
            @PathVariable Long idVehicule,
            @Valid @RequestBody EquipementDTO equipementDTO) {
        return vehiculeService.affecterEquipementAVehicule(idVehicule, equipementDTO);
    }

    @Operation(summary = "Supprimer un véhicule")
    @ApiResponse(responseCode = "204", description = "Véhicule supprimé")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        vehiculeService.deleteById(id);
    }
}
