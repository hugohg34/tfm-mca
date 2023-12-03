package mca.house_keeping_service.room.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mca.house_keeping_service.reservation.model.Reservation;
import mca.house_keeping_service.room.model.Room;
import mca.house_keeping_service.room.model.RoomReservationDetail;

public interface RoomReservationDetailRepository extends JpaRepository<RoomReservationDetail, Long> {

	RoomReservationDetail findByReservationAndRoom(Reservation reservation, Room room);

	@Query("SELECT rrd FROM RoomReservationDetail rrd JOIN rrd.reservation res JOIN rrd.room rm WHERE res.checkInDate >= :givenDate OR res.checkOutDate <= :givenDate AND rm.establishment.id = :establishmentId")
	List<RoomReservationDetail> findByReservationDateAndEstablishmentId(LocalDate givenDate, UUID establishmentId);



}
