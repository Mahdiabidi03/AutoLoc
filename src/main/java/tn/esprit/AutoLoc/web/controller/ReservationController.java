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
import tn.esprit.AutoLoc.service.IReservationService;
import tn.esprit.AutoLoc.web.dto.ReservationDTO;

@Tag(name = "Réservations", description = "Gestion des réservations")
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;

    @Operation(summary = "Lister les réservations")
    @ApiResponse(responseCode = "200", description = "Liste des réservations")
    @GetMapping
    public List<ReservationDTO> findAll() {
        return reservationService.findAll();
    }

    @Operation(summary = "Consulter une réservation")
    @ApiResponse(responseCode = "200", description = "Réservation trouvée")
    @ApiResponse(responseCode = "404", description = "Réservation introuvable")
    @GetMapping("/{id}")
    public ReservationDTO findById(@PathVariable Long id) {
        return reservationService.findById(id);
    }

    @Operation(summary = "Créer une réservation")
    @ApiResponse(responseCode = "201", description = "Réservation créée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDTO create(@Valid @RequestBody ReservationDTO dto) {
        return reservationService.create(dto);
    }

    @Operation(summary = "Modifier une réservation")
    @ApiResponse(responseCode = "200", description = "Réservation modifiée")
    @ApiResponse(responseCode = "404", description = "Réservation introuvable")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PutMapping("/{id}")
    public ReservationDTO update(@PathVariable Long id, @Valid @RequestBody ReservationDTO dto) {
        return reservationService.update(id, dto);
    }

    @Operation(summary = "Supprimer une réservation")
    @ApiResponse(responseCode = "204", description = "Réservation supprimée")
    @ApiResponse(responseCode = "404", description = "Réservation introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        reservationService.deleteById(id);
    }
}
