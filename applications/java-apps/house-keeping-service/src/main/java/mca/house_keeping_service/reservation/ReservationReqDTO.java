package mca.house_keeping_service.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ReservationReqDTO {

	private LocalDate checkInDate;
	private LocalDate checkOutDate;
	private String guestName;
	private LocalDateTime actualArrivalTime;
	private LocalDateTime actualDepartureTime;

	private UUID establishmentId;
	private Map<UUID, Integer> roomTypes;

}
