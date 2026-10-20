package tn.esprit.AutoLoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.AutoLoc.domain.Reservation;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {
}
