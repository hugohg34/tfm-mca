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

import jakarta.transaction.Transactional;
import mca.house_keeping_service.establishment.model.Establishment;
import mca.house_keeping_service.establishment.repository.EstablishmentRepository;
import mca.house_keeping_service.guest.model.Guest;
import mca.house_keeping_service.guest.repository.GuestRepository;
import mca.house_keeping_service.reservation.ReservationRepository;
import mca.house_keeping_service.reservation.model.Reservation;
import mca.house_keeping_service.room.model.Room;
import mca.house_keeping_service.room.model.RoomReservationDetail;
import mca.house_keeping_service.room.model.RoomType;
import mca.house_keeping_service.room.repository.RoomRepository;
import mca.house_keeping_service.room.repository.RoomReservationDetailRepository;
import mca.house_keeping_service.room.repository.RoomTypeRepository;

@Component
@Profile("local")
public class DatabasePopulator {

	private int numRoomsByType = 20;
	private int numEstablishments = 200;
	private int numTypeRoomByEstab = 5;
	private int numGuests = 1000;
	private String uuidPattern = "00000000-0000-0000-0000-%012d";

	private Logger logger = LoggerFactory.getLogger(DatabasePopulator.class);
	private EstablishmentRepository estabRepo;
	private RoomRepository roomRepo;
	private RoomTypeRepository rTypeRepo;
	private ReservationRepository reservRepos;
	private GuestRepository guestRepo;
	private RoomReservationDetailRepository roomResDetailRepo;

	public DatabasePopulator(EstablishmentRepository estabRepo, RoomTypeRepository rTypeRepo,
			RoomRepository roomRepo, ReservationRepository reservRepos,
			GuestRepository guestRepo,
			RoomReservationDetailRepository roomResDetailRepo) {
		this.estabRepo = estabRepo;
		this.rTypeRepo = rTypeRepo;
		this.roomRepo = roomRepo;
		this.reservRepos = reservRepos;
		this.guestRepo = guestRepo;
		this.roomResDetailRepo = roomResDetailRepo;
	}

	@EventListener

	public void populate(ContextRefreshedEvent event) {
		if (estabRepo.count() > 0) {
			return;
		}
		createGuest();

		List<Establishment> estabList = createEstablishments();
		estabList.forEach(estab -> createRoomTypes(estab)
				.forEach(rType -> createRooms(estab, rType)));

		Establishment estab = estabList.get(0);
		createReservation(estab);
		logger.info("Database populated");
	}

	@Transactional
	private List<Establishment> createEstablishments() {
		List<Establishment> estabList = new ArrayList<>();
		for (int i = 1; i <= numEstablishments; i++) {
			UUID uuid = UUID.fromString(String.format(uuidPattern, i));
			Establishment estab = new Establishment();
			estab.setId(uuid);
			estab.setName("Establishment " + i);
			estabList.add(estab);
		}
		estabRepo.saveAll(estabList);
		return estabList;
	}

	@Transactional
	private List<RoomType> createRoomTypes(Establishment estab) {
		List<RoomType> roomTypeList = new ArrayList<>();
		for (int i = 1; i <= numTypeRoomByEstab; i++) {
			RoomType roomType = new RoomType();
			roomType.setName("RoomType" + i);
			roomType.setBedType("TWIN");
			roomType.setDescription("Room two beds");
			roomType.setGuestCapacity(2);
			roomType.setNumberOfRooms(numRoomsByType);
			roomType.setEstablishment(estab);
			roomTypeList.add(roomType);
		}
		rTypeRepo.saveAll(roomTypeList);
		return roomTypeList;
	}

	private void createRooms(Establishment establishment, RoomType roomType) {
		List<Room> rooms = new ArrayList<>();
		for (int i = 1; i <= numRoomsByType; i++) {
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
		roomRepo.saveAll(rooms);
	}

	private List<Guest> createGuest() {
		List<Guest> guestList = new ArrayList<>();
		for (int i = 1; i <= numGuests; i++) {
			Guest guest = Guest.builder()
					.name("Guest N " + i)
					.surname("Ramon y Cajal")
					.secondSurname("Cajal").phoneNumber("123456789").email("alan@turing.com")
					.roomPreference("No smoking, away from elevator, alegic to cats")
					.comments("Best guest ever, please give him a discount")
					.idNumber("12345678A").build();
			guestList.add(guest);
		}
		guestRepo.saveAll(guestList);
		return guestList;
	}

	@Transactional
	private void createReservation(Establishment estab) {
		List<Reservation> reservList = new ArrayList<>();
		List<RoomReservationDetail> roomResDetailList = new ArrayList<>();
		List<Room> roomList = roomRepo.findByEstablishmentIdOrderByRoomNumberAsc(estab.getId());
		for (Room room : roomList) {
			Reservation reservation = new Reservation();
			reservation.setCheckInDate(LocalDate.now());
			reservation.setCheckOutDate(reservation.getCheckInDate().plusDays(1));
			reservation.setEstablishment(estab);
			reservation.setReservationName("Alan Turing");
			reservation.addRoomType(room.getRoomType(), 1);
			reservList.add(reservation);
		}
		reservRepos.saveAll(reservList);
		for (int i = 0; i < reservList.size(); i++) {
			roomResDetailList.add(new RoomReservationDetail(reservList.get(i), roomList.get(i)));
		}

		roomResDetailRepo.saveAll(roomResDetailList);

	}

}
