package mca.house_keeping_service.reservation.dto;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResRoomTypesDTO {

	@NotNull
	private UUID roomTypeId;
	@Min(1)	
	private Integer quantity;

}
