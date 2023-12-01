package mca.house_keeping_service.room.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RoomDTO {

	@NotNull
    private UUID id;

    @Size(max = 255)
    private String name;

    @NotNull
    private UUID establishment;

}
