package mca.house_keeping_service.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import mca.house_keeping_service.reservation.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}