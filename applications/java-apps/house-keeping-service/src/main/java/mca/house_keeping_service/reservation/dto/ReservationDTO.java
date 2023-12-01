package mca.house_keeping_service.reservation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ReservationDTO {

	@NotBlank
	private Long id;
	
	@NotBlank
	private LocalDate checkInDate;
	
	@NotBlank
	private LocalDate checkOutDate;
	
	@NotBlank
	@Size(max = 100)
	private String reservationName;
	
	private LocalDateTime actualArrivalTime;
	private LocalDateTime actualDepartureTime;
	
	@NotBlank
	private UUID establishmentId;
	private Long holderId;

	@Builder.Default
	@Valid
	private List<ResRoomTypesDTO> roomTypes = new ArrayList<>();

	public void addRoomType(UUID roomTypeId, int quantity) {
		roomTypes.add(new ResRoomTypesDTO(roomTypeId, Integer.valueOf(quantity)));
	}

}
