package mca.house_keeping_service.reservation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ReservationId {

	private Long value;
	
	@JsonProperty("reservationId")
	public Long getValue() {
		return value;
	}

}
