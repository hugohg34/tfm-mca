package mca.house_keeping_service.room.model;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mca.house_keeping_service.reservation.model.Reservation;

@Entity
@Getter
@NoArgsConstructor
@Table(uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"room_id", "reservation_id"})
	})
public class RoomReservationDetail {

	@Id
	@Column(nullable = false, updatable = false)
	@Tsid
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    private String guestName;
    
    public RoomReservationDetail(Reservation reservation, Room room) {
        if(reservation.getEstablishment().getId() != room.getEstablishment().getId()) {
            throw new IllegalArgumentException(String.format(
                "Reservation (ID: %s) and room (ID: %s) must be from the same establishment.",
                reservation.getId(), room.getId()));
        }
    	this.reservation = reservation;
        this.room = room;
    }

    public RoomReservationDetail(Reservation reservation, Room room, String guestName) {
        this(reservation, room);
        this.guestName = guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

}
