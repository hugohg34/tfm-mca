package mca.house_keeping_service.reservation.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import mca.house_keeping_service.establishment.model.GuestId;
import mca.house_keeping_service.reservation.dto.ReservationDTO;
import mca.house_keeping_service.reservation.dto.ReservationReqDTO;
import mca.house_keeping_service.reservation.model.ReservationId;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1/reservation", produces = MediaType.APPLICATION_JSON_VALUE)
public interface ReservationRestInterface {

	@Operation(summary = "Get reservation details")
	@ApiResponse(description = "Reservation details", responseCode = "200")
	@GetMapping("/{id}")
	public ResponseEntity<ReservationDTO> getReservation(
			@Parameter(description = "Unique identifier for the Reservation") @PathVariable(name = "id") final ReservationId resId);

	@Operation(summary = "Create a new reservation")
	@ApiResponse(description = "ID reservation",  responseCode = "201")
	@PostMapping
	public ResponseEntity<ReservationId> createReservation(
			@Parameter(description = "Reservation request") @RequestBody @Valid final ReservationReqDTO reservationReqDTO);

	@Operation(summary = "Checkin a reservation")
	@ApiResponse(description = "ID reservation",  responseCode = "200")	
	@PutMapping("/{id}/checkin")
	public ResponseEntity<ReservationId> checkin(
			@Parameter(description = "Unique identifier for the Reservation") @PathVariable(name = "id") final ReservationId resId,
			@Parameter(description = "Unique identifier for the holder") @RequestBody final GuestId holderId);

    @Operation(summary = "Assign rooms to a reservation")
    @PostMapping("/{reservationId}/rooms")
    @ApiResponse(description = "Rooms assigned to the Reservation", responseCode = "204")
	public ResponseEntity<Void> addRooms(
            @Parameter(description = "Reservation ID")
            @PathVariable ReservationId reservationId,
			@Parameter(description = "Unique identifier for rooms") 
			@RequestBody final List<UUID> roomsId);

	@Operation(summary = "checkout a reservation")
	@ApiResponse(description = "ID reservation",  responseCode = "200")
	@PutMapping("/{id}/checkout")
	public ResponseEntity<ReservationId> checkout(
			@Parameter(description = "Unique identifier for the Reservation") @PathVariable(name = "id") final ReservationId resId);
}
