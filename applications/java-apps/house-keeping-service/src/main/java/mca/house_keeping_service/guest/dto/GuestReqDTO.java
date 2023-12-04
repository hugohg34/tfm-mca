package mca.house_keeping_service.guest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GuestReqDTO {

	@NotNull
	private String name;
	private String surname;
	private String secondSurname;
	private String phoneNumber;

    @NotNull
	private String email;
	private String roomPreference;
	private String comments;

    @NotNull
	private String idNumber;

}
