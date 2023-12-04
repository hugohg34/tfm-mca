package mca.house_keeping_service.room.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import mca.house_keeping_service.reservation.ReservationRepository;
import mca.house_keeping_service.reservation.model.Reservation;
import mca.house_keeping_service.reservation.model.ReservationId;
import mca.house_keeping_service.room.dto.RoomRackDTO;
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
	Logger logger = LoggerFactory.getLogger(RackService.class);

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

		if (rooms.size() != roomsId.size()) {
			throw new NotFoundException("Room/s not found for establishment");
		}

		List<RoomReservationDetail> roomResDetail = new ArrayList<>();
		for (Room room : rooms) {
			RoomReservationDetail detail = roomResDetailRepo.findByReservationAndRoom(reservation, room);
			if (detail == null) {
				roomResDetail.add(new RoomReservationDetail(reservation, room));
			}
		}
		roomResDetailRepo.saveAll(roomResDetail);
	}

	public List<RoomRackDTO> gerRackOf(UUID establishmentId) {
		logger.info("log to check observability");
		List<RoomReservationDetail> roomResDetail = roomResDetailRepo
				.findByReservationDateAndEstablishmentId(LocalDate.now(), establishmentId);
		List<Room> rooms = roomRepo.findByEstablishmentIdOrderByRoomNumberAsc(establishmentId);

		List<RoomRackDTO> roomRackDTOs = new ArrayList<>();
		for (Room room : rooms) {
			RoomRackDTO roomRackDTO = mapToDTO(room);
			roomResDetail.stream()
					.filter(detail -> detail.getRoom().getId().equals(room.getId()))
					.findFirst()
					.ifPresent(detail -> roomRackDTO.setReservationHolder(detail.getGuestName()));
			roomRackDTOs.add(roomRackDTO);
		}

		return roomRackDTOs;
	}

	@Transactional
	public RoomRackDTO cleanRoom(UUID establishmentId, UUID roomId) {
		Room room = roomRepo.findById(roomId)
				.orElseThrow(() -> new NotFoundException("Room not found"));
		if (!room.getEstablishment().getId().equals(establishmentId)) {
			throw new NotFoundException("Room not found for establishment");
		}
		room.setClean(true);
		roomRepo.save(room);
		return mapToDTO(room);
	}

	@Transactional
	public RoomRackDTO dirtyRoom(UUID establishmentId, UUID roomId) {
		Room room = roomRepo.findById(roomId)
				.orElseThrow(() -> new NotFoundException("Room not found"));
		if (!room.getEstablishment().getId().equals(establishmentId)) {
			throw new NotFoundException("Room not found for establishment");
		}
		room.setClean(false);
		roomRepo.save(room);
		return mapToDTO(room);
	}

	@Transactional
	public RoomRackDTO supervisedRoom(UUID establishmentId, UUID roomId) {
		Room room = roomRepo.findById(roomId)
				.orElseThrow(() -> new NotFoundException("Room not found"));
		if (!room.getEstablishment().getId().equals(establishmentId)) {
			throw new NotFoundException("Room not found for establishment");
		}
		room.supervised();
		roomRepo.save(room);
		return mapToDTO(room);
	}

	private RoomRackDTO mapToDTO(Room room) {
		return RoomRackDTO.builder()
				.id(room.getId())
				.name(room.getName())
				.roomNumber(room.getRoomNumber())
				.isClean(room.isClean())
				.isSupervised(room.isSupervised())
				.incidentActive(room.isIncidentActive())
				.isOccupied(room.isOccupied())
				.roomType(room.getRoomType().getName())
				.guestCapacity(room.getRoomType().getGuestCapacity())
				.build();
	}
}
