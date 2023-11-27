package mca.house_keeping_service.establishment.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class GuestId {
	private Long value;

    @JsonProperty("guestId")
	public Long getValue() {
		return value;
	}

}
