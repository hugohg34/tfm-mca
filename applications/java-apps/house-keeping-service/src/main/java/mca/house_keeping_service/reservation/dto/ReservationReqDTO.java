package mca.house_keeping_service.reservation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ReservationReqDTO {

	@NotNull
	private LocalDate checkInDate;
	
	@NotNull
	private LocalDate checkOutDate;
	
	@NotNull
	@Size(max = 100)
	private String reservationName;
	
	private LocalDateTime actualArrivalTime;
	private LocalDateTime actualDepartureTime;

	@NotNull
	private UUID establishmentId;
	private Long hoderId;

	@Builder.Default
	@Valid
	private List<ResRoomTypesDTO> roomTypes = new ArrayList<>();

	public void addRoomType(UUID roomTypeId, Integer quantity) {
		roomTypes.add(new ResRoomTypesDTO(roomTypeId, quantity));
	}

}
