package mca.house_keeping_service.room.model;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mca.house_keeping_service.establishment.model.Establishment;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "establishment", "roomType" })
public class Room {
	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "establishment_id", nullable = false)
	private Establishment establishment;

	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoomReservationDetail> roomAssignments;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_type_id", nullable = false)
	private RoomType roomType;
	
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private OffsetDateTime dateCreated;
	
	@LastModifiedDate
	@Column(nullable = false)
	private OffsetDateTime lastUpdated;
	
	@Column(nullable = false)
	private Integer roomNumber;
	
	@Column(nullable = false)
	private boolean isClean = true;
	
	@Column(nullable = false)
	private boolean isSupervised;
	
	@Column(nullable = false)
	private boolean incidentActive;
	
	@Column(nullable = false)
	private boolean isOccupied = false;

	public boolean isVacant() {
		return !incidentActive && !isOccupied();
	}
	
	public boolean isReadyForOccupancy() {
        return isClean && isSupervised && isVacant();
	}
	
}