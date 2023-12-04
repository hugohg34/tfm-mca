package mca.house_keeping_service.guest.model;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mca.house_keeping_service.reservation.model.Reservation;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Guest {
	
	@Id
	@Column(nullable = false, updatable = false)
	@Tsid
	private Long id;
	private String name;
	private String surname;
	private String secondSurname;
	private String phoneNumber;
	private String email;
	private String roomPreference;
	private String comments;
	private String idNumber;
	
	@ManyToMany(mappedBy = "guests")
	@Builder.Default
	private Set<Reservation> reservations = new HashSet<>();
	
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private OffsetDateTime dateCreated;

	@LastModifiedDate
	@Column(nullable = false)
	private OffsetDateTime lastUpdated;
}
