package mca.house_keeping_service.establishment.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import mca.house_keeping_service.establishment.dto.EstablishmentRespDTO;

@RequestMapping(value = "/api/establishments", produces = MediaType.APPLICATION_JSON_VALUE)
public interface EstablishmentRestInterface {

	@GetMapping
	public ResponseEntity<Page<EstablishmentRespDTO>> getAllEstablishments(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size,
			@RequestParam(defaultValue = "id") String sortBy);

	@GetMapping("/{id}")
	public ResponseEntity<EstablishmentRespDTO> getEstablishment(
			@Parameter(description = "Establishment ID", example = "00000000-0000-0000-0000-000000000001")
			@PathVariable(name = "id") final UUID id);

	@PostMapping
	@ApiResponse(responseCode = "201")
	public ResponseEntity<UUID> createEstablishment(
			@RequestBody @Valid final EstablishmentRespDTO establishmentDTO);

	@PutMapping("/{id}")
	public ResponseEntity<UUID> updateEstablishment(
			@Parameter(description = "Establishment ID", example = "00000000-0000-0000-0000-000000000001")
			@PathVariable(name = "id") final UUID id,
			@RequestBody @Valid final EstablishmentRespDTO establishmentDTO);

	@DeleteMapping("/{id}")
	@ApiResponse(responseCode = "204")
	public ResponseEntity<Void> deleteEstablishment(
			@Parameter(description = "Establishment ID", example = "00000000-0000-0000-0000-000000000001")
			@PathVariable(name = "id") final UUID id);

}
