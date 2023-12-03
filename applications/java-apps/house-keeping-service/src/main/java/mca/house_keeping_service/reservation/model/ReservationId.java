package mca.house_keeping_service.reservation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ReservationId {

	private Long value;
	
	@JsonProperty("reservationId")
    @Schema(type = "long", description = "The reservation ID")
	public Long getValue() {
		return value;
	}

}
