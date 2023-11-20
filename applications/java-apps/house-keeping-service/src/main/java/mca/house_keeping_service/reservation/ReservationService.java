package mca.house_keeping_service.reservation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mca.house_keeping_service.establishment.model.Establishment;
import mca.house_keeping_service.establishment.repository.EstablishmentRepository;
import mca.house_keeping_service.room.model.RoomType;
import mca.house_keeping_service.room.repository.RoomTypeRepository;
import mca.house_keeping_service.util.NotFoundException;

@Service
public class ReservationService {

	private final ReservationRepository reservRepo;
	private final EstablishmentRepository estabRepo;
	private RoomTypeRepository roomTypeRepo;

	public ReservationService(ReservationRepository reservRepo, EstablishmentRepository estabRepo,
			RoomTypeRepository roomTypeRepo) {
		this.reservRepo = reservRepo;
		this.estabRepo = estabRepo;
		this.roomTypeRepo = roomTypeRepo;
	}

	@Transactional
	public ReservationDTO get(UUID id) {
		return reservRepo.findById(id)
				.map(this::mapToDTO)
				.orElseThrow(() -> new RuntimeException("Reservation not found"));
	}

	private ReservationDTO mapToDTO(Reservation reservation) {
		ReservationDTO resDTO = ReservationDTO.builder()
				.id(reservation.getId())
				.checkInDate(reservation.getCheckInDate())
				.checkOutDate(reservation.getCheckOutDate())
				.guestName(reservation.getGuestName())
				.actualArrivalTime(reservation.getActualArrivalTime().orElse(null))
				.actualDepartureTime(reservation.getActualDepartureTime().orElse(null))
				.establishmentId(reservation.getEstablishment().getId())
				.build();

		reservation.getRoomTypes().forEach((roomType, quantity) -> resDTO.addRoomType(roomType.getId(), quantity));

		return resDTO;
	}

	public UUID create(@Valid ReservationReqDTO reservationReqDTO) {
		Establishment establishment = estabRepo.findById(reservationReqDTO.getEstablishmentId())
				.orElseThrow(() -> new NotFoundException("Establishment not found"));

		Reservation reservation = new Reservation();
		reservation.setCheckInDate(reservationReqDTO.getCheckInDate());
		reservation.setCheckOutDate(reservationReqDTO.getCheckOutDate());
		reservation.setGuestName(reservationReqDTO.getGuestName());
		reservation.setEstablishment(establishment);

		reservationReqDTO.getRoomTypes().forEach((roomTypeId, quantity) -> {
			RoomType roomType = roomTypeRepo.findById(roomTypeId)
					.orElseThrow(() -> new NotFoundException("RoomType not found"));

			reservation.addRoomType(roomType, quantity);
		});

		Map<RoomType, Integer> roomTypes = new HashMap<>();
		for (Map.Entry<UUID, Integer> entry : reservationReqDTO.getRoomTypes().entrySet()) {
			RoomType roomType = roomTypeRepo.findById(entry.getKey())
					.orElseThrow(() -> new NotFoundException("RoomType not found"));
			roomTypes.put(roomType, entry.getValue());
		}

		reservRepo.save(reservation);

		return reservation.getId();
	}

}
