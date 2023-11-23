package mca.house_keeping_service.room.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RoomRackDTO {
	private UUID id;

	private String name;

	private Integer roomNumber;
	
	private String reservationHolder;

	private boolean isClean;

	private boolean isSupervised;

	private boolean incidentActive;

	private boolean isOccupied;

	private String roomType;

	private int guestCapacity;

}
