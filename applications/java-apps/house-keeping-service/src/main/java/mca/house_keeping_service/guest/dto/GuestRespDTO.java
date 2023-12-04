package mca.house_keeping_service.guest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GuestRespDTO {

	private Long id;
	private String name;
	private String surname;
	private String secondSurname;
	private String phoneNumber;
	private String email;
	private String roomPreference;
	private String comments;
	private String idNumber;

}
