package mca.house_keeping_service.room.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mca.house_keeping_service.establishment.model.Establishment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "establishment" })
public class RoomType {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(updatable = false, nullable = false)
	private UUID id;

	private String name;
	private String description;
	private int guestCapacity;
	private String bedType;
	private int numberOfRooms;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "establishment_id", nullable = false)
	private Establishment establishment;
}
