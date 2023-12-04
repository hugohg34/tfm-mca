package mca.house_keeping_service.room.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RoomRespDTO {

	private UUID id;
	private String name;
	private UUID establishmentId;
	private UUID roomTypeId;
	private Integer roomNumber;
	private boolean isClean;
	private boolean isSupervised;
	private boolean incidentActive;
	private boolean isOccupied;

}
