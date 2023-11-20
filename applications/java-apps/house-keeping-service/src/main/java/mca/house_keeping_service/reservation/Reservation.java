package mca.house_keeping_service.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mca.house_keeping_service.establishment.model.Establishment;
import mca.house_keeping_service.room.model.RoomType;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "establishment" })
public class Reservation {
	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private LocalDate checkInDate;

	@Column(nullable = false)
	private LocalDate checkOutDate;

	@Column(nullable = false, length = 100)
	private String guestName;

	private LocalDateTime actualArrivalTime;
	private LocalDateTime actualDepartureTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "establishment_id", nullable = false)
	private Establishment establishment;

	@ElementCollection
	@CollectionTable(name = "reservation_room_type", joinColumns = @JoinColumn(name = "reservation_id"))
	@MapKeyJoinColumn(name = "room_type_id")
	@Column(name = "roomTypes")
	private Map<RoomType, Integer> roomTypes = new HashMap<>();

	public Optional<LocalDateTime> getActualArrivalTime() {
		return Optional.ofNullable(actualArrivalTime);
	}

	public void setActualArrivalTime(Optional<LocalDateTime> actualArrivalTime) {
		this.actualArrivalTime = actualArrivalTime.orElse(null);
	}

	public Optional<LocalDateTime> getActualDepartureTime() {
		return Optional.ofNullable(actualDepartureTime);
	}

	public void setActualDepartureTime(Optional<LocalDateTime> actualDepartureTime) {
		this.actualDepartureTime = actualDepartureTime.orElse(null);
	}

	public void addRoomType(RoomType roomType, int numberOfRooms) {
		roomTypes.put(roomType, Integer.valueOf(numberOfRooms));
	}

	public void removeRoomType(RoomType roomType) {
		roomTypes.remove(roomType);
	}

}
