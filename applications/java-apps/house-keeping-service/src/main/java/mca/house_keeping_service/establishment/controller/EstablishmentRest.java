package mca.house_keeping_service.establishment.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import mca.house_keeping_service.establishment.dto.EstablishmentReqDTO;
import mca.house_keeping_service.establishment.dto.EstablishmentRespDTO;
import mca.house_keeping_service.establishment.service.EstablishmentService;
import mca.house_keeping_service.room.dto.RoomRackDTO;
import mca.house_keeping_service.room.dto.RoomReqDTO;
import mca.house_keeping_service.room.dto.RoomRespDTO;
import mca.house_keeping_service.room.dto.RoomTypeDTO;
import mca.house_keeping_service.room.dto.RoomTypeReqDTO;
import mca.house_keeping_service.room.service.RackService;
import mca.house_keeping_service.room.service.RoomService;
import mca.house_keeping_service.util.PageSizeTooLargeException;

@RestController
public class EstablishmentRest implements EstablishmentRestInterface {

	private final EstablishmentService establishmentService;
	private final RoomService roomService;
	private final RackService rackService;
	private static final int MAX_PAGE_SIZE = 50;

	public EstablishmentRest(EstablishmentService establishmentService,
			RoomService roomService, RackService rackService) {
		this.establishmentService = establishmentService;
		this.roomService = roomService;
		this.rackService = rackService;
	}

	@Override
	public ResponseEntity<Page<EstablishmentRespDTO>> getAllEstablishments(
			int page, int size, String sortBy) {
		if (size > MAX_PAGE_SIZE) {
			throw new PageSizeTooLargeException("Maximum page size allowed is " + MAX_PAGE_SIZE);
		}
		Page<EstablishmentRespDTO> resultPage = establishmentService.findAllPaginatedAndSorted(page, size, sortBy);
		return ResponseEntity.ok(resultPage);
	}

	@Override
	public ResponseEntity<EstablishmentRespDTO> getEstablishment(
			final UUID id) {
		return ResponseEntity.ok(establishmentService.get(id));
	}

	@Override
	public ResponseEntity<UUID> createEstablishment(
			final EstablishmentReqDTO establishmentDTO) {
		final UUID createdId = establishmentService.create(establishmentDTO);
		return new ResponseEntity<>(createdId, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<UUID> updateEstablishment(final UUID id,
			final EstablishmentReqDTO establishmentDTO) {
		establishmentService.update(id, establishmentDTO);
		return ResponseEntity.ok(id);
	}

	@Override
	public ResponseEntity<Void> deleteEstablishment(final UUID id) {
		establishmentService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@Override
	public List<RoomRackDTO> getRackof(UUID establishmentId) {
		return rackService.gerRackOf(establishmentId);
	}

	@Override
	public List<RoomTypeDTO> getRoomTypes(UUID establishmentId) {
		return roomService.getRoomTypes(establishmentId);
	}

	@Override
	public RoomRackDTO cleanRoom(UUID establishmentId, UUID roomId) {
		return rackService.cleanRoom(establishmentId, roomId);
	}

	@Override
	public RoomRackDTO dirtyRoom(UUID establishmentId, UUID roomId) {
		return rackService.dirtyRoom(establishmentId, roomId);
	}

	@Override
	public RoomRackDTO supervisedRoom(UUID establishmentId, UUID roomId) {
		return rackService.supervisedRoom(establishmentId, roomId);
	}

	@Override
	public UUID createRoomType(UUID establishmentId, RoomTypeReqDTO roomTypeReqDTO) {
		return roomService.createRoomType(establishmentId, roomTypeReqDTO);
	}

	@Override
	public RoomRespDTO createRoom(RoomReqDTO room) {
		return roomService.create(room);
	}
}
