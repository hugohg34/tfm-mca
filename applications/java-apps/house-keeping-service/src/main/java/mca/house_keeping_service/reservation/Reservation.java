package mca.house_keeping_service.reservation;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

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

	private LocalDateTime checkInDate;
	private LocalDateTime checkOutDate;
	private String status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "establishment_id", nullable = false)
	private Establishment establishment;

	@ElementCollection
	@CollectionTable(name = "reservation_room_type", joinColumns = @JoinColumn(name = "reservation_id"))
	@MapKeyJoinColumn(name = "room_type_id")
	@Column(name = "roomTypes")
	private Map<RoomType, Integer> roomTypes;
}
