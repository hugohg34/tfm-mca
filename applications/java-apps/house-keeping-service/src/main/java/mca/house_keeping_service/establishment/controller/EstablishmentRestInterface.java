package mca.house_keeping_service.establishment.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import mca.house_keeping_service.establishment.dto.EstablishmentReqDTO;
import mca.house_keeping_service.establishment.dto.EstablishmentRespDTO;
import mca.house_keeping_service.room.dto.RoomRackDTO;
import mca.house_keeping_service.room.dto.RoomReqDTO;
import mca.house_keeping_service.room.dto.RoomRespDTO;
import mca.house_keeping_service.room.dto.RoomTypeDTO;
import mca.house_keeping_service.room.dto.RoomTypeReqDTO;
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1/establishments", produces = MediaType.APPLICATION_JSON_VALUE)
public interface EstablishmentRestInterface {

	@Operation(summary = "Get a list of all establishments")
	@ApiResponse(description = "List of establishments", responseCode = "200")
	@GetMapping
	public ResponseEntity<Page<EstablishmentRespDTO>> getAllEstablishments(
			@Parameter(description = "Page number, starts from 0", example = "0")
			@RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Number of elements per page", example = "20")
			@RequestParam(defaultValue = "20") int size,
			@Parameter(description = "Sorting criteria in the format: property(,asc|desc). "
					+ "Default sort order is ascending. "
					+ "Multiple sort criteria are supported.", example = "name,asc")
			@RequestParam(defaultValue = "id") String sortBy);

	@Operation(summary = "Get a specific establishment")
	@ApiResponse(description = "Establishment details", responseCode = "200")
	@GetMapping("/{id}")
	public ResponseEntity<EstablishmentRespDTO> getEstablishment(
			@Parameter(description = "Establishment ID", example = "00000000-0000-0000-0000-000000000001")
			@PathVariable(name = "id") UUID id);

	@Operation(summary = "Create a new establishment")
	@ApiResponse(description = "Establishment created", responseCode = "201")
	@PostMapping
	public ResponseEntity<EstablishmentRespDTO> createEstablishment(
			@RequestBody @Valid EstablishmentReqDTO establishmentDTO);

	@Operation(summary = "Update an existing establishment")
	@ApiResponse(description = "ID establishment", responseCode = "200")
	@PutMapping("/{id}")
	public ResponseEntity<UUID> updateEstablishment(
			@Parameter(description = "Establishment ID", example = "00000000-0000-0000-0000-000000000001")
			@PathVariable(name = "id") UUID id,
			@RequestBody @Valid EstablishmentReqDTO establishmentDTO);

	@Operation(summary = "Delete an existing establishment")
	@ApiResponse(responseCode = "204")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEstablishment(
			@Parameter(description = "Establishment ID", example = "00000000-0000-0000-0000-000000000001")
			@PathVariable(name = "id") UUID id);

	@Operation(summary = "Get a list of rooms in the establishment")
	@ApiResponse(description = "List of rooms UUID", responseCode = "200")
	@GetMapping("/{establishmentId}/rooms")
	List<RoomRackDTO> getRackof(
			@Parameter(description = "Establishment ID", example = "00000000-0000-0000-0000-000000000001")
			@PathVariable UUID establishmentId);
	
	@Operation(summary = "Get a list of all roomTypes for a specific establishment")
	@ApiResponse(description = "List of room types", responseCode = "200")
	@GetMapping("/{establishmentId}/room-types")
	List<RoomTypeDTO> getRoomTypes(
			@Parameter(description = "Establishment ID", example = "00000000-0000-0000-0000-000000000001")
			@PathVariable UUID establishmentId);

	@Operation(summary = "Create a new roomType for a specific establishment")
	@ApiResponse(description = "ID room type", responseCode = "201")
	@PostMapping("/{establishmentId}/room-types")
	ResponseEntity<UUID> createRoomType(
			@Parameter(description = "Establishment ID", example = "00000000-0000-0000-0000-000000000001")
			@PathVariable UUID establishmentId,
			@RequestBody @Valid RoomTypeReqDTO roomTypeReqDTO);
	
	@Operation(summary = "Clean a room")
	@ApiResponse(description = "RoomRackDTO", responseCode = "200")
	@PutMapping("/{establishmentId}/room/{roomId}/clean")
	RoomRackDTO cleanRoom(
			@Parameter(description = "Establishment ID", example = "00000000-0000-0000-0000-000000000001")
			@PathVariable UUID establishmentId,
			@Parameter(description = "Room ID", example = "00000000-0000-0000-0000-000000000001")
			@PathVariable UUID roomId);

	@Operation(summary = "Dirty a room")
	@ApiResponse(description = "RoomRackDTO", responseCode = "200")
	@PutMapping("/{establishmentId}/room/{roomId}/dirty")
	RoomRackDTO dirtyRoom(
			@Parameter(description = "Establishment ID", example = "00000000-0000-0000-0000-000000000001")
			@PathVariable UUID establishmentId,
			@Parameter(description = "Room ID", example = "00000000-0000-0000-0000-000000000001")
			@PathVariable UUID roomId);

	@Operation(summary = "Supervised a room")
	@ApiResponse(description = "RoomRackDTO", responseCode = "200")
	@PutMapping("/{establishmentId}/room/{roomId}/supervised")
	RoomRackDTO supervisedRoom(
			@Parameter(description = "Establishment ID", example = "00000000-0000-0000-0000-000000000001")
			@PathVariable UUID establishmentId,
			@Parameter(description = "Room ID", example = "00000000-0000-0000-0000-000000000001")
			@PathVariable UUID roomId);
	
	@Operation(summary = "Create new room")
	@ApiResponse(description = "Room created", responseCode = "201")
    @PostMapping("/{establishmentId}/rooms")
	ResponseEntity<RoomRespDTO> createRoom(@RequestBody RoomReqDTO room);

}
