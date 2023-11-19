package mca.house_keeping_service.config;

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
import mca.house_keeping_service.establishment.repository.EstablishmentRepository;
import mca.house_keeping_service.room.model.Room;
import mca.house_keeping_service.room.model.RoomType;
import mca.house_keeping_service.room.repository.RoomRepository;
import mca.house_keeping_service.room.repository.RoomTypeRepository;

@Component
@Profile("local")
public class DatabasePopulator {

	private EstablishmentRepository eRepository;
	private RoomRepository rRepository;
	private RoomTypeRepository rTypeRepository;
	Logger logger = LoggerFactory.getLogger(DatabasePopulator.class);

	public DatabasePopulator(EstablishmentRepository eRepository, RoomTypeRepository rTypeRepository,
			RoomRepository rRepository) {
		this.eRepository = eRepository;
		this.rTypeRepository = rTypeRepository;
		this.rRepository = rRepository;
	}

	@EventListener
	public void populate(ContextRefreshedEvent event) {
		if (eRepository.count() == 0) {
			Establishment e1 = new Establishment();
			e1.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
			e1.setName("Hotel 1");
			eRepository.save(e1);

			RoomType roomType = new RoomType();
			roomType.setBedType("TWIN");
			roomType.setDescription("Doble dos camas");
			roomType.setGuestCapacity(2);
			roomType.setNumberOfRooms(100);
			roomType.setEstablishment(e1);
			rTypeRepository.save(roomType);

			List<Room> rooms = createRooms(e1, roomType);
			rooms.forEach(room -> rRepository.save(room));
			logger.info("Database populated");
		}
	}

	private List<Room> createRooms(Establishment establishment, RoomType roomType) {
		List<Room> rooms = new ArrayList<>();
		for (int i = 1; i < 50; i++) {
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

}
