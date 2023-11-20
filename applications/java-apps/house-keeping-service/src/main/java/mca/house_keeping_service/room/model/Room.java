package mca.house_keeping_service.room.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	/*
	private String specialRequest;
	private String reservationStatus;
	private String currentGuestName;
	private String nextGuestName;
	private String observations;
*/

	public boolean isVacant() {
		return !incidentActive && !isOccupied();
	}
	
	public boolean isReadyForOccupancy() {
        return isClean && isSupervised && isVacant();
	}

	/*
	 * public Optional<String> getSpecialRequest() { return
	 * Optional.ofNullable(specialRequest); }
	 * 
	 * public void setSpecialRequest(Optional<String> specialRequest) {
	 * this.specialRequest = specialRequest.orElse(null); }
	 * 
	 * public Optional<String> getReservationStatus() { return
	 * Optional.ofNullable(reservationStatus); }
	 * 
	 * public void setReservationStatus(Optional<String> reservationStatus) {
	 * this.reservationStatus = reservationStatus.orElse(null); }
	 * 
	 * public Optional<LocalDateTime> getCheckInTime() { return
	 * Optional.ofNullable(checkInTime); }
	 * 
	 * public void setCheckInTime(Optional<LocalDateTime> checkInTime) {
	 * this.checkInTime = checkInTime.orElse(null); }
	 * 
	 * public Optional<LocalDateTime> getCheckOutTime() { return
	 * Optional.ofNullable(checkOutTime); }
	 * 
	 * public void setCheckOutTime(Optional<LocalDateTime> checkOutTime) {
	 * this.checkOutTime = checkOutTime.orElse(null); }
	 * 
	 * public Optional<String> getCurrentGuestName() { return
	 * Optional.ofNullable(currentGuestName); }
	 * 
	 * public void setCurrentGuestName(Optional<String> currentGuestName) {
	 * this.currentGuestName = currentGuestName.orElse(null); }
	 * 
	 * public Optional<String> getNextGuestName() { return
	 * Optional.ofNullable(nextGuestName); }
	 * 
	 * public void setNextGuestName(Optional<String> nextGuestName) {
	 * this.nextGuestName = nextGuestName.orElse(null); }
	 * 
	 * public Optional<String> getObservations() { return
	 * Optional.ofNullable(observations); }
	 * 
	 * public void setObservations(Optional<String> observations) {
	 * this.observations = observations.orElse(null); }
	 */
	
}