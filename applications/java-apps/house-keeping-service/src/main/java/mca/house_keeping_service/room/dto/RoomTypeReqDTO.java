package mca.house_keeping_service.room.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoomTypeReqDTO {
	private String name;
	private String description;
	private int guestCapacity;
	private String bedType;
	private int numberOfRooms;
	private UUID establishmentId;
}
