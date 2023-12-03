package mca.house_keeping_service.room.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import mca.house_keeping_service.establishment.model.Establishment;
import mca.house_keeping_service.establishment.repository.EstablishmentRepository;
import mca.house_keeping_service.room.dto.RoomDTO;
import mca.house_keeping_service.room.dto.RoomTypeDTO;
import mca.house_keeping_service.room.model.Room;
import mca.house_keeping_service.room.model.RoomType;
import mca.house_keeping_service.room.repository.RoomRepository;
import mca.house_keeping_service.room.repository.RoomTypeRepository;
import mca.house_keeping_service.util.NotFoundException;

@Service
public class RoomService {

	private final RoomRepository roomRepo;
	private final EstablishmentRepository establismentRepo;
	private final RoomTypeRepository roomTypeRepo;

	public RoomService(final RoomRepository roomRepository,
			final EstablishmentRepository establishmentRepo,
			final RoomTypeRepository roomTypeRepo) {
		this.roomRepo = roomRepository;
		this.establismentRepo = establishmentRepo;
		this.roomTypeRepo = roomTypeRepo;
	}

	public RoomDTO get(final UUID id) {
		return roomRepo.findById(id)
				.map(this::mapToDTO)
				.orElseThrow(NotFoundException::new);
	}

	public UUID create(final RoomDTO roomDTO) {
		final Room room = new Room();
		mapToEntity(roomDTO, room);
		return roomRepo.save(room).getId();
	}

	public void update(final UUID id, final RoomDTO roomDTO) {
		final Room room = roomRepo.findById(id)
				.orElseThrow(NotFoundException::new);
		mapToEntity(roomDTO, room);
		roomRepo.save(room);
	}

	public void delete(final UUID id) {
		roomRepo.deleteById(id);
	}

	public List<RoomTypeDTO> getRoomTypes(UUID establishmentId) {
		List<RoomType> roomTypes = roomTypeRepo.findByEstablishmentId(establishmentId);
		return roomTypes.stream().map(this::mapToDTO).toList();
	}

	private RoomTypeDTO mapToDTO(RoomType roomType) {
		return RoomTypeDTO.builder()
				.id(roomType.getId())
				.name(roomType.getName())
				.description(roomType.getDescription())
				.guestCapacity(roomType.getGuestCapacity())
				.bedType(roomType.getBedType())
				.numberOfRooms(roomType.getNumberOfRooms())
				.establishmentId(roomType.getEstablishment().getId())
				.build();
	}

	private RoomDTO mapToDTO(Room room) {
		RoomDTO roomDTO = new RoomDTO();
		roomDTO.setId(room.getId());
		roomDTO.setName(room.getName());
		roomDTO.setEstablishment(room.getEstablishment() == null ? null : room.getEstablishment().getId());
		return roomDTO;
	}

	private Room mapToEntity(final RoomDTO roomDTO, final Room room) {
		room.setName(roomDTO.getName());
		final Establishment establishment = roomDTO.getEstablishment() == null ? null
				: establismentRepo.findById(roomDTO.getEstablishment())
						.orElseThrow(() -> new NotFoundException("establishment not found"));
		room.setEstablishment(establishment);
		return room;
	}

}
