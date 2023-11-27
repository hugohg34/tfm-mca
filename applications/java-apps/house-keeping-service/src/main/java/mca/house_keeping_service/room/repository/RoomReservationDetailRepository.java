package mca.house_keeping_service.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mca.house_keeping_service.reservation.model.Reservation;
import mca.house_keeping_service.room.model.Room;
import mca.house_keeping_service.room.model.RoomReservationDetail;

public interface RoomReservationDetailRepository extends JpaRepository<RoomReservationDetail, Long> {

	RoomReservationDetail findByReservationAndRoom(Reservation reservation, Room room);

}
