package mca.house_keeping_service.reservation;

import java.util.Set;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mca.house_keeping_service.establishment.model.Establishment;
import mca.house_keeping_service.establishment.model.Guest;
import mca.house_keeping_service.establishment.model.GuestId;
import mca.house_keeping_service.establishment.repository.EstablishmentRepository;
import mca.house_keeping_service.establishment.repository.GuestRepository;
import mca.house_keeping_service.reservation.dto.ResRoomTypesDTO;
import mca.house_keeping_service.reservation.dto.ReservationDTO;
import mca.house_keeping_service.reservation.dto.ReservationReqDTO;
import mca.house_keeping_service.reservation.model.Reservation;
import mca.house_keeping_service.reservation.model.ReservationId;
import mca.house_keeping_service.room.model.RoomReservationDetail;
import mca.house_keeping_service.room.model.RoomType;
import mca.house_keeping_service.room.repository.RoomTypeRepository;
import mca.house_keeping_service.util.NotFoundException;
import mca.house_keeping_service.util.PreconditionException;

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
	public ReservationDTO get(ReservationId resId) {
		return reservRepo.findById(resId.getValue())
				.map(this::mapToDTO)
				.orElseThrow(() -> new NotFoundException("Reservation not found"));
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

	public ReservationId create(@Valid ReservationReqDTO reservationReqDTO) {
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

		return new ReservationId(reservation.getId());
	}

	@Transactional
	public ReservationId checkin(ReservationId resId, GuestId holderId) {
		Reservation reservation = reservRepo.findById(resId.getValue())
				.orElseThrow(() -> new NotFoundException("Reservation not found"));
		
		Guest holder = guestRepo.findById(holderId.getValue())
				.orElseThrow(() -> new NotFoundException("Holder not found"));

		reservation.checkin(holder);
		
		Set<RoomReservationDetail> romResDetailSet = reservation.getRoomAssignments();
		romResDetailSet.forEach(roomResDetail -> {
			if(!roomResDetail.getRoom().isReadyForOccupancy()) {
				throw new PreconditionException("Room not ready for occupancy");
			}
			roomResDetail.setGuestName(holder.getName());
			roomResDetail.getRoom().setOccupied(true);
		});
				
		reservRepo.save(reservation);
		
		return resId;
	}

}
