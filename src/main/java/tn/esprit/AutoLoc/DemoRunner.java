package tn.esprit.AutoLoc;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tn.esprit.AutoLoc.domain.Agence;
import tn.esprit.AutoLoc.domain.CategorieVehicule;
import tn.esprit.AutoLoc.domain.StatutVehicule;
import tn.esprit.AutoLoc.domain.Vehicule;
import tn.esprit.AutoLoc.exception.ResourceNotFoundException;
import tn.esprit.AutoLoc.service.IAgenceService;
import tn.esprit.AutoLoc.service.IVehiculeService;

@Slf4j
@Component
@RequiredArgsConstructor
public class DemoRunner implements CommandLineRunner {

    private final IAgenceService agenceService;
    private final IVehiculeService vehiculeService;

    @Override
    public void run(String... args) {
        Agence agence = new Agence();
        agence.setNom("Agence Tunis Centre");
        agence.setVille("Tunis");
        agence = agenceService.create(agence);

        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("123 TUN 4567");
        vehicule.setMarque("Renault");
        vehicule.setModele("Clio");
        vehicule.setCategorie(CategorieVehicule.CITADINE);
        vehicule.setTarifJournalier(new BigDecimal("80"));
        vehicule.setStatut(StatutVehicule.DISPONIBLE);
        vehicule = vehiculeService.create(vehicule);

        // Test 1 : cas normal
        Vehicule affecte = agenceService.affecterVehiculeAAgence(
                vehicule.getIdVehicule(), agence.getIdAgence());
        log.info("TEST 1 OK : véhicule {} affecté à l'agence {}",
                affecte.getIdVehicule(), affecte.getAgence().getIdAgence());

        // Test 2 : agence inexistante
        try {
            agenceService.affecterVehiculeAAgence(vehicule.getIdVehicule(), 9999L);
        } catch (ResourceNotFoundException e) {
            log.info("TEST 2 OK : {}", e.getMessage());
        }

        // Test 3 : véhicule inexistant
        try {
            agenceService.affecterVehiculeAAgence(9999L, agence.getIdAgence());
        } catch (ResourceNotFoundException e) {
            log.info("TEST 3 OK : {}", e.getMessage());
        }
    }
}