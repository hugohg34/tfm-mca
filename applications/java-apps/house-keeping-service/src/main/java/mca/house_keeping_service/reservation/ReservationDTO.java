package mca.house_keeping_service.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ReservationDTO {

    private UUID id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String guestName;
    private LocalDateTime actualArrivalTime;
    private LocalDateTime actualDepartureTime;
    private UUID establishmentId;
    
    @Builder.Default
    private List<ResRoomTypesDTO> roomTypes = new ArrayList<>();

    public void addRoomType(UUID roomTypeId, int quantity) {
        roomTypes.add(new ResRoomTypesDTO(roomTypeId, Integer.valueOf(quantity)));
    }

}
