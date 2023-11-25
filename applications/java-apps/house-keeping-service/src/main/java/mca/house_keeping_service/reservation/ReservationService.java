package mca.house_keeping_service.reservation;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mca.house_keeping_service.establishment.model.Establishment;
import mca.house_keeping_service.establishment.model.Guest;
import mca.house_keeping_service.establishment.repository.EstablishmentRepository;
import mca.house_keeping_service.establishment.repository.GuestRepository;
import mca.house_keeping_service.room.model.RoomType;
import mca.house_keeping_service.room.repository.RoomTypeRepository;
import mca.house_keeping_service.util.NotFoundException;

@Service
public class ReservationService {

	private final ReservationRepository reservRepo;
	private final EstablishmentRepository estabRepo;
	private final RoomTypeRepository roomTypeRepo;
	private final GuestRepository guestRepo;

	public ReservationService(ReservationRepository reservRepo, EstablishmentRepository estabRepo,
			RoomTypeRepository roomTypeRepo, GuestRepository guestRepo) {
		this.reservRepo = reservRepo;
		this.estabRepo = estabRepo;
		this.roomTypeRepo = roomTypeRepo;
		this.guestRepo = guestRepo;
	}

	@Transactional
	public ReservationDTO get(Long id) {
		return reservRepo.findById(id)
				.map(this::mapToDTO)
				.orElseThrow(() -> new RuntimeException("Reservation not found"));
	}

	private ReservationDTO mapToDTO(Reservation reservation) {
		ReservationDTO resDTO = ReservationDTO.builder()
				.id(reservation.getId())
				.checkInDate(reservation.getCheckInDate())
				.checkOutDate(reservation.getCheckOutDate())
				.reservationName(reservation.getReservationName())
				.actualArrivalTime(reservation.getActualArrivalTime().orElse(null))
				.actualDepartureTime(reservation.getActualDepartureTime().orElse(null))
				.establishmentId(reservation.getEstablishment().getId())
				.holderId(reservation.getHolder().getId())
				.build();

		reservation.getRoomTypes().forEach((roomType, quantity) -> resDTO.addRoomType(roomType.getId(), quantity));

		return resDTO;
	}

	public Long create(@Valid ReservationReqDTO reservationReqDTO) {
		Establishment establishment = estabRepo.findById(reservationReqDTO.getEstablishmentId())
				.orElseThrow(() -> new NotFoundException("Establishment not found"));
		
		Guest holder = guestRepo.findById(reservationReqDTO.getHoderId())
				.orElseThrow(() -> new NotFoundException("Holder not found"));

		Reservation reservation = new Reservation();
		reservation.setCheckInDate(reservationReqDTO.getCheckInDate());
		reservation.setCheckOutDate(reservationReqDTO.getCheckOutDate());
		reservation.setReservationName(reservationReqDTO.getReservationName());
		reservation.setEstablishment(establishment);
		reservation.setHolder(holder);

		for (ResRoomTypesDTO resRoomTypesDTO : reservationReqDTO.getRoomTypes()) {
			RoomType roomType = roomTypeRepo.findById(resRoomTypesDTO.getRoomTypeId())
					.orElseThrow(() -> new NotFoundException("RoomType not found"));
			reservation.addRoomType(roomType, resRoomTypesDTO.getQuantity());
		}

		reservRepo.save(reservation);

		return reservation.getId();
	}

}
