package mca.house_keeping_service.room.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import mca.house_keeping_service.reservation.model.ReservationId;
import mca.house_keeping_service.room.dto.RoomRackDTO;
import mca.house_keeping_service.room.dto.RoomTypeDTO;
import mca.house_keeping_service.room.service.RackService;
import mca.house_keeping_service.room.service.RoomService;

@RestController
public class RoomRackController implements RoomRackStatusInterface {

	private RoomService roomService;
	private RackService rackService;

	public RoomRackController(RoomService roomService,
			RackService rackService) {
		this.roomService = roomService;
		this.rackService = rackService;
	}
    @Override
    public List<RoomRackDTO> getRackof(UUID establishmentId) {
        return roomService.gerRackOf(establishmentId);
    }

    @Override
    public List<RoomTypeDTO> getRoomTypes(UUID establishmentId) {
    
        return roomService.getRoomTypes(establishmentId);
    }
    
	@Override
	public ResponseEntity<Void> addRooms(ReservationId reservationId, List<UUID> roomsId) {
		rackService.addRooms(reservationId, roomsId);
		return ResponseEntity.noContent().build();
	}
    
}
