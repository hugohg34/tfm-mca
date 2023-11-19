package mca.house_keeping_service.room.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RestController;

import mca.house_keeping_service.room.dto.RoomRackDTO;
import mca.house_keeping_service.room.service.RoomService;

@RestController
public class RoomRackController implements RoomRackStatusInterface {

	private RoomService roomService;

	public RoomRackController(RoomService roomService) {
		this.roomService = roomService;
	}
    @Override
    public List<RoomRackDTO> getRackof(UUID establishmentId) {
        return roomService.gerRackOf(establishmentId);
    }

    @Override
    public RoomRackDTO getRoomById(UUID establishmentId, UUID roomId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRoomById'");
    }
    
}
