package mca.house_keeping_service.reservation.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mca.house_keeping_service.establishment.model.Establishment;
import mca.house_keeping_service.establishment.model.Guest;
import mca.house_keeping_service.room.model.RoomReservationDetail;
import mca.house_keeping_service.room.model.RoomType;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "establishment", "guests", "roomAssignments" })
public class Reservation {
	@Id
	@Column(nullable = false, updatable = false)
	@Tsid
	private Long id;

	@Column(nullable = false)
	private LocalDate checkInDate;

	@Column(nullable = false)
	private LocalDate checkOutDate;

	@Column(nullable = false, length = 100)
	private String reservationName;

	@JoinColumn(name = "holder_id", nullable = true)
	@ManyToOne(fetch = FetchType.LAZY)
	private Guest holder;

	@ManyToMany(cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE
	})
	@JoinTable(name = "reservation_guest", joinColumns = @JoinColumn(name = "reservation_id"), inverseJoinColumns = @JoinColumn(name = "guest_id"))
	private Set<Guest> guests;
	
	@OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoomReservationDetail> roomAssignments;

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

	public Optional<LocalDateTime> getActualDepartureTime() {
		return Optional.ofNullable(actualDepartureTime);
	}


	public void addRoomType(RoomType roomType, int numberOfRooms) {
		roomTypes.put(roomType, Integer.valueOf(numberOfRooms));
	}

	public void removeRoomType(RoomType roomType) {
		roomTypes.remove(roomType);
	}

	public void addGuest(Guest guest) {
		guests.add(guest);
		guest.getReservations().add(this);
	}

	public void removeGuest(Guest guest) {
		guests.remove(guest);
		guest.getReservations().remove(this);
	}
	
	public void checkin(Guest holder) {
		this.holder = holder;
		this.actualArrivalTime = LocalDateTime.now();
		this.actualDepartureTime = null;
	}

}
