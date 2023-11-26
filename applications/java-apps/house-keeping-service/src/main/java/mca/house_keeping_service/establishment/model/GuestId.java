package mca.house_keeping_service.establishment.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GuestId {
	private final Long value;

    @JsonProperty("guestId")
	public Long getValue() {
		return value;
	}

}
