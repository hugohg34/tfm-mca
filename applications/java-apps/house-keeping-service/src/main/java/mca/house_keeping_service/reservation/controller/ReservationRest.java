package mca.house_keeping_service.reservation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import mca.house_keeping_service.establishment.model.GuestId;
import mca.house_keeping_service.reservation.ReservationService;
import mca.house_keeping_service.reservation.dto.ReservationDTO;
import mca.house_keeping_service.reservation.dto.ReservationReqDTO;
import mca.house_keeping_service.reservation.model.ReservationId;

@RestController
public class ReservationRest implements ReservationRestInterface {

	private final ReservationService reservationService;

	public ReservationRest(ReservationService reservationService) {
		this.reservationService = reservationService;
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

}
