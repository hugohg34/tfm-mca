package mca.house_keeping_service.reservation.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import mca.house_keeping_service.establishment.model.GuestId;
import mca.house_keeping_service.reservation.dto.ReservationDTO;
import mca.house_keeping_service.reservation.dto.ReservationReqDTO;
import mca.house_keeping_service.reservation.model.ReservationId;

@RequestMapping(value = "/api/reservation", produces = MediaType.APPLICATION_JSON_VALUE)
public interface ReservationRestInterface {

	@GetMapping("/{id}")
	public ResponseEntity<ReservationDTO> getReservation(
			@Parameter(description = "Unique identifier for the Reservation") @PathVariable(name = "id") final ReservationId resId);

	@PostMapping
	@ApiResponse(responseCode = "201")
	public ResponseEntity<ReservationId> createReservation(
			@Parameter(description = "Reservation request") @RequestBody @Valid final ReservationReqDTO reservationReqDTO);

	@PutMapping("/{id}/checkin")
	public ResponseEntity<ReservationId> checkin(
			@Parameter(description = "Unique identifier for the Reservation") @PathVariable(name = "id") final ReservationId resId,
			@Parameter(description = "Unique identifier for the holder") @RequestBody final GuestId holderId);

}
