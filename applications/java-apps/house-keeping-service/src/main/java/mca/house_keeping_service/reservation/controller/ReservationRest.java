package mca.house_keeping_service.reservation.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import mca.house_keeping_service.guest.model.GuestId;
import mca.house_keeping_service.reservation.ReservationService;
import mca.house_keeping_service.reservation.dto.ReservationDTO;
import mca.house_keeping_service.reservation.dto.ReservationReqDTO;
import mca.house_keeping_service.reservation.model.ReservationId;
import mca.house_keeping_service.room.service.RackService;

@RestController
public class ReservationRest implements ReservationRestInterface {

	private final ReservationService reservationService;
	private final RackService rackService;

	public ReservationRest(ReservationService reservationService, RackService rackService) {
		this.reservationService = reservationService;
		this.rackService = rackService;
	}

	public ResponseEntity<ReservationDTO> getReservation(
			final ReservationId resId) {
		return ResponseEntity.ok(reservationService.get(resId));
	}

	public ResponseEntity<ReservationId> createReservation(
			final ReservationReqDTO reservationReqDTO) {
		final ReservationId createdId = reservationService.create(reservationReqDTO);
		return new ResponseEntity<>(createdId, HttpStatus.CREATED);
	}

	public ResponseEntity<ReservationId> checkin(
			final ReservationId resId,
			final GuestId holderId) {
		final ReservationId resUpdate = reservationService.checkin(resId, holderId);
		return ResponseEntity.ok(resUpdate);
	}

	public ResponseEntity<Void> addRooms(ReservationId reservationId, List<UUID> roomsId) {
		rackService.addRooms(reservationId, roomsId);
		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<ReservationId> checkout(
			final ReservationId resId) {
		final ReservationId resUpdate = reservationService.checkout(resId);
		return ResponseEntity.ok(resUpdate);
	}

}
