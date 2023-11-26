package mca.house_keeping_service.reservation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReservationId {

	private final Long value;
	
	@JsonProperty("reservationId")
	public Long getValue() {
		return value;
	}

}
