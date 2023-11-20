package mca.house_keeping_service.reservation;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResRoomTypesDTO {
    
    private UUID roomTypeId;
    private Integer quantity;

}
