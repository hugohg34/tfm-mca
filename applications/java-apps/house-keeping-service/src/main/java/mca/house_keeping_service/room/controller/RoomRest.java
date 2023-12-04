package mca.house_keeping_service.room.controller;

import java.util.UUID;

import mca.house_keeping_service.room.dto.RoomReqDTO;
import mca.house_keeping_service.room.dto.RoomRespDTO;
import mca.house_keeping_service.room.service.RoomService;

public class RoomRest implements RoomRestInterface {

    RoomService roomService;

    public RoomRest(RoomService roomService) {
        this.roomService = roomService;
    }

	@Override
	public RoomRespDTO getRoomById(UUID id) {
        return roomService.get(id);
	}

	@Override
	public RoomRespDTO createRoom(RoomReqDTO room) {
        return roomService.create(room);
	}

    

}
