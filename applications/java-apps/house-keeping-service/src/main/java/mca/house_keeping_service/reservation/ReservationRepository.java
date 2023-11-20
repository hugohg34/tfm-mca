package mca.house_keeping_service.reservation;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    
}