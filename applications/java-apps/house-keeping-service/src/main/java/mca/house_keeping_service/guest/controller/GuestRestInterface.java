package mca.house_keeping_service.guest.controller;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import mca.house_keeping_service.guest.dto.GuestReqDTO;
import mca.house_keeping_service.guest.dto.GuestRespDTO;
import mca.house_keeping_service.guest.model.GuestId;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1/guests", produces = MediaType.APPLICATION_JSON_VALUE)
public interface GuestRestInterface {

	@Operation(summary = "Get guest by id")
	@ApiResponse(responseCode = "200", description = "Guest found")
	@GetMapping("/{id}")
	GuestRespDTO getGuestById(@PathVariable("id") GuestId id);

	@Operation(summary = "Create new guest")
	@ApiResponse(responseCode = "201", description = "Guest created")
	@PostMapping
	ResponseEntity<GuestRespDTO> createGuest(@RequestBody GuestReqDTO guest);

	@Operation(summary = "Update guest")
	@ApiResponse(responseCode = "200", description = "Guest updated")
	@PutMapping("/{id}")
	GuestRespDTO updateGuest(@PathVariable("id") GuestId id, @RequestBody GuestReqDTO guest);

	@Operation(summary = "Delete guest")
	@ApiResponse(responseCode = "204", description = "Guest deleted")
	@DeleteMapping("/{id}")
	ResponseEntity<Void> deleteGuest(@PathVariable("id") GuestId id);

}
