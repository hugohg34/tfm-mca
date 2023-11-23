package mca.house_keeping_service.room.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import mca.house_keeping_service.room.dto.RoomRackDTO;
import mca.house_keeping_service.room.dto.RoomTypeDTO;

@RequestMapping(value = "/api/establishments/{establishmentId}/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
public interface RoomRackStatusInterface {

    @Operation(summary = "Get a list of all rooms for a specific establishment")
    @ApiResponse(description = "List of rooms", responseCode = "200")
    @GetMapping("/")
    List<RoomRackDTO> getRackof(
        @Parameter(description = "Establishment ID", example = "00000000-0000-0000-0000-000000000000")
        @PathVariable UUID establishmentId);

    @Operation(summary = "Get a list of all roomTypes for a specific establishment")
    @ApiResponse(description = "List of room types", responseCode = "200")
    @GetMapping("/types")
	List<RoomTypeDTO> getRoomTypes(
			@Parameter(description = "Establishment ID", example = "00000000-0000-0000-0000-000000000000")
			@PathVariable UUID establishmentId);

}
