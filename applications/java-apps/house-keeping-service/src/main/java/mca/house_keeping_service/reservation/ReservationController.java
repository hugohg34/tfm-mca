package mca.house_keeping_service.reservation;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/reservation", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationController {

	private final ReservationService reservationService;

	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<ReservationDTO> getReservation(
			@PathVariable(name = "id") final Long id) {
		return ResponseEntity.ok(reservationService.get(id));
	}

	@PostMapping
	@ApiResponse(responseCode = "201")
	public ResponseEntity<Long> createReservation(
			@RequestBody @Valid final ReservationReqDTO reservationReqDTO) {
		final Long createdId = reservationService.create(reservationReqDTO);
		return new ResponseEntity<>(createdId, HttpStatus.CREATED);
	}

}
