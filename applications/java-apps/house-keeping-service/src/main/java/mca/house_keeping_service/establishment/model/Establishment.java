package mca.house_keeping_service.establishment.model;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import mca.house_keeping_service.room.model.Room;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Establishment {

	@Id
	@Column(nullable = false, updatable = false)
	private UUID id;

	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "establishment")
	private Set<Room> rooms;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private OffsetDateTime dateCreated;

	@LastModifiedDate
	@Column(nullable = false)
	private OffsetDateTime lastUpdated;

}
