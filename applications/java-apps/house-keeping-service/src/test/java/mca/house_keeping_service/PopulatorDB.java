package mca.house_keeping_service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mca.house_keeping_service.establishment.model.Establishment;
import mca.house_keeping_service.establishment.model.Guest;
import mca.house_keeping_service.establishment.repository.EstablishmentRepository;
import mca.house_keeping_service.establishment.repository.GuestRepository;
import mca.house_keeping_service.reservation.ReservationRepository;
import mca.house_keeping_service.reservation.model.Reservation;
import mca.house_keeping_service.room.model.Room;
import mca.house_keeping_service.room.model.RoomType;
import mca.house_keeping_service.room.repository.RoomRepository;
import mca.house_keeping_service.room.repository.RoomTypeRepository;

@Component
public class PopulatorDB {

	private EstablishmentRepository estabRepo;
	private RoomRepository roomRepo;
	private RoomTypeRepository roomTypeRepo;
	private ReservationRepository reservationRepo;
	private GuestRepository guestRepo;
	private Establishment establishmentDB;
	private RoomType roomTypeDB;
	private List<Room> rooms;
	private Reservation reservation;
	private Guest guest;

	@Autowired
	public PopulatorDB(EstablishmentRepository estabRepo, RoomRepository roomRepo, RoomTypeRepository roomTypeRepo,
			ReservationRepository reservationRepo, GuestRepository guestRepo) {
		this.estabRepo = estabRepo;
		this.roomRepo = roomRepo;
		this.roomTypeRepo = roomTypeRepo;
		this.reservationRepo = reservationRepo;
		this.guestRepo = guestRepo;
	}

	public void populate() {
		establishmentDB = createEstablishment();
		estabRepo.save(establishmentDB);
		roomTypeDB = createRoomTypeTwin(establishmentDB);
		roomTypeRepo.save(roomTypeDB);
		rooms = createRooms(establishmentDB, roomTypeDB);
		roomRepo.saveAll(rooms);
		guest = createGuest();
		guestRepo.save(guest);

		reservation = new Reservation();
		reservation.setEstablishment(establishmentDB);
		reservation.setCheckInDate(LocalDate.now());
		reservation.setCheckOutDate(LocalDate.now().plusDays(1));
		reservation.addRoomType(roomTypeDB, 2);
		reservation.setReservationName("Liskov");
		reservationRepo.save(reservation);
	}

	public Establishment getEstablishmentDB() {
		return establishmentDB;
	}

	public RoomType getRoomTypeDB() {
		return roomTypeDB;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public Guest getGuest() {
		return guest;
	}

	private Establishment createEstablishment() {
		Establishment estab = new Establishment();
		estab.setId(UUID.fromString("00000000-0000-0000-0000-000000000001"));
		estab.setName("Hotel 1");
		return estab;
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
			room.setSupervised(true);
			room.setIncidentActive(false);
			room.setOccupied(false);
			rooms.add(room);
		}
		return rooms;
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
	
	private Guest createGuest() {
		return Guest.builder()
			.name("Guest")
			.surname("Surname")
			.secondSurname("Second Surname")
			.phoneNumber("123456789")
			.email("email@nobody.com")
			.roomPreference("Room Preference")
			.comments("Comments")
			.idNumber("12345678A")
			.build();

		
	}
}