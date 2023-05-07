package Reservation.MS.ReservationBack.queries.repository;

import Reservation.MS.ReservationBack.queries.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository  extends JpaRepository<Reservation,String> {
}
