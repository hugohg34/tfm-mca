package mca.house_keeping_service.establishment.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EstablishmentReqDTO {
	
	@NotNull
	private UUID id;
	
	@NotNull
	@Size(max = 255)
	private String name;

}
