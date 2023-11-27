package mca.house_keeping_service.room.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import mca.house_keeping_service.reservation.ReservationRepository;
import mca.house_keeping_service.reservation.model.Reservation;
import mca.house_keeping_service.reservation.model.ReservationId;
import mca.house_keeping_service.room.model.Room;
import mca.house_keeping_service.room.model.RoomReservationDetail;
import mca.house_keeping_service.room.repository.RoomRepository;
import mca.house_keeping_service.room.repository.RoomReservationDetailRepository;
import mca.house_keeping_service.util.NotFoundException;

@Service
public class RackService {

    private final RoomRepository roomRepo;
    private final ReservationRepository reservationRepo;
    private final RoomReservationDetailRepository roomResDetailRepo;

	public RackService(RoomRepository roomRepo, 
        ReservationRepository reservationRepo,
        RoomReservationDetailRepository roomResDetailRepo) {
            this.roomRepo = roomRepo;
            this.reservationRepo = reservationRepo;
            this.roomResDetailRepo = roomResDetailRepo;		
	}

	
	@Transactional
	public void addRooms(ReservationId reservationId, List<UUID> roomsId) {		
        Reservation reservation = reservationRepo.findById(reservationId.getValue())
            .orElseThrow(() -> new NotFoundException("Reservation not found"));
        List<Room> rooms = roomRepo.findAllById(roomsId);
        
        if(rooms.size() != roomsId.size()) {
            throw new NotFoundException("Room/s not found for establishment");
        }

        List<RoomReservationDetail> roomResDetail = new ArrayList<>();
        for(Room room : rooms) {
        	RoomReservationDetail detail = roomResDetailRepo.findByReservationAndRoom(reservation, room);
        	if(detail == null) {
        		roomResDetail.add(new RoomReservationDetail(reservation, room));
        	}
        }
        roomResDetailRepo.saveAll(roomResDetail);
	}
	
}
