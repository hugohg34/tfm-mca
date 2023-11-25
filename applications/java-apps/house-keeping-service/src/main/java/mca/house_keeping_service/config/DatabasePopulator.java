package mca.house_keeping_service.config;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import mca.house_keeping_service.establishment.model.Establishment;
import mca.house_keeping_service.establishment.model.Guest;
import mca.house_keeping_service.establishment.repository.EstablishmentRepository;
import mca.house_keeping_service.establishment.repository.GuestRepository;
import mca.house_keeping_service.reservation.Reservation;
import mca.house_keeping_service.reservation.ReservationRepository;
import mca.house_keeping_service.room.model.Room;
import mca.house_keeping_service.room.model.RoomType;
import mca.house_keeping_service.room.repository.RoomRepository;
import mca.house_keeping_service.room.repository.RoomTypeRepository;

@Component
@Profile("local")
public class DatabasePopulator {

	private Logger logger = LoggerFactory.getLogger(DatabasePopulator.class);
	private EstablishmentRepository eRepository;
	private RoomRepository rRepository;
	private RoomTypeRepository rTypeRepository;
	private ReservationRepository resRepository;
	private GuestRepository guestRepository;

	public DatabasePopulator(EstablishmentRepository eRepository, RoomTypeRepository rTypeRepository,
			RoomRepository rRepository, ReservationRepository resRepository,
			GuestRepository guestRepository) {
		this.eRepository = eRepository;
		this.rTypeRepository = rTypeRepository;
		this.rRepository = rRepository;
		this.resRepository = resRepository;
		this.guestRepository = guestRepository;
	}

	@EventListener
	public void populate(ContextRefreshedEvent event) {
		if (eRepository.count() == 0) {
			Establishment e1 = new Establishment();
			e1.setId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
			e1.setName("Hotel 1");
			eRepository.save(e1);

			RoomType roomTypeTwin = createRoomTypeTwin(e1);
			rTypeRepository.save(roomTypeTwin);
			List<Room> roomsTw = createRooms(e1, roomTypeTwin);
			roomsTw.forEach(room -> rRepository.save(room));

			RoomType roomTypeJS = createRoomTypeJS(e1);
			rTypeRepository.save(roomTypeJS);
			List<Room> roomsJ = createRooms(e1, roomTypeJS);
			roomsJ.forEach(room -> rRepository.save(room));

			Guest guest = Guest.builder().name("Alan").surname("Turing")
					.secondSurname("Turing").phoneNumber("123456789").email("alan@turing.com")
					.roomPreference("No smoking, away from elevator, alegic to cats")
					.comments("Best guest ever, please give him a discount")
					.idNumber("12345678A").build();
			guestRepository.save(guest);

			Reservation reservation = createReservation(e1);
			reservation.addRoomType(roomTypeTwin, 3);
			reservation.setHolder(guest);
			resRepository.save(reservation);

			logger.info("Database populated");
		}
	}

	private RoomType createRoomTypeTwin(Establishment estab) {
		RoomType roomType = new RoomType();
		roomType.setName("Doble Twin");
		roomType.setBedType("TWIN");
		roomType.setDescription("Hab. Doble dos camas");
		roomType.setGuestCapacity(2);
		roomType.setNumberOfRooms(20);
		roomType.setEstablishment(estab);
		return roomType;
	}

	private RoomType createRoomTypeJS(Establishment estab) {
		RoomType roomType = new RoomType();
		roomType.setName("Junior Suite");
		roomType.setBedType("King Size");
		roomType.setDescription("Hab. Junior Suite con salita");
		roomType.setGuestCapacity(2);
		roomType.setNumberOfRooms(20);
		roomType.setEstablishment(estab);
		return roomType;
	}

	private List<Room> createRooms(Establishment establishment, RoomType roomType) {
		List<Room> rooms = new ArrayList<>();
		for (int i = 1; i < 20; i++) {
			Room room = new Room();
			room.setName("Room " + i);
			room.setEstablishment(establishment);
			room.setRoomNumber(i);
			room.setRoomType(roomType);
			room.setClean(true);
			room.setSupervised(false);
			room.setIncidentActive(false);
			rooms.add(room);
		}
		return rooms;
	}

	private Reservation createReservation(Establishment establishment) {
		Reservation reservation = new Reservation();
		reservation.setCheckInDate(LocalDate.now());
		reservation.setCheckOutDate(reservation.getCheckInDate().plusDays(1));
		reservation.setEstablishment(establishment);
		reservation.setReservationName("Alan Turing");
		return reservation;
	}

}
