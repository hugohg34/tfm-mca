package mca.house_keeping_service.room.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import mca.house_keeping_service.establishment.model.Establishment;
import mca.house_keeping_service.establishment.repository.EstablishmentRepository;
import mca.house_keeping_service.room.dto.RoomReqDTO;
import mca.house_keeping_service.room.dto.RoomRespDTO;
import mca.house_keeping_service.room.dto.RoomTypeDTO;
import mca.house_keeping_service.room.model.Room;
import mca.house_keeping_service.room.model.RoomType;
import mca.house_keeping_service.room.repository.RoomRepository;
import mca.house_keeping_service.room.repository.RoomTypeRepository;
import mca.house_keeping_service.util.NotFoundException;

@Service
public class RoomService {

	private RoomRepository roomRepo;
	private EstablishmentRepository establismentRepo;
	private RoomTypeRepository roomTypeRepo;

	public RoomService(RoomRepository roomRepository,
			EstablishmentRepository establishmentRepo,
			RoomTypeRepository roomTypeRepo) {
		this.roomRepo = roomRepository;
		this.establismentRepo = establishmentRepo;
		this.roomTypeRepo = roomTypeRepo;
	}

	public RoomRespDTO get(UUID id) {
		return roomRepo.findById(id)
				.map(this::mapToDTO)
				.orElseThrow(NotFoundException::new);
	}

	public RoomRespDTO create(RoomReqDTO roomReqDTO) {
		Room room = new Room();
		mapToEntity(roomReqDTO, room);
		roomRepo.save(room);
		return mapToDTO(room);
	}

	public RoomRespDTO update(UUID id, RoomReqDTO roomReqDTO) {
		Room room = roomRepo.findById(id)
				.orElseThrow(NotFoundException::new);
		mapToEntity(roomReqDTO, room);
		roomRepo.save(room);
		return mapToDTO(room);
	}

	public void delete(UUID id) {
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

	private RoomRespDTO mapToDTO(Room room) {
		return RoomRespDTO.builder()
				.id(room.getId())
				.name(room.getName())
				.establishmentId(room.getEstablishment().getId())
				.roomTypeId(room.getRoomType().getId())
				.roomNumber(room.getRoomNumber())
				.incidentActive(room.isIncidentActive())
				.isClean(room.isClean())
				.isSupervised(room.isSupervised())
				.isOccupied(room.isOccupied())
				.build();
	}

	private Room mapToEntity(RoomReqDTO roomReqDTO, Room room) {
		room.setName(roomReqDTO.getName());
		Establishment establishment = establismentRepo.findById(roomReqDTO.getEstablishmentId())
				.orElseThrow(() -> new NotFoundException("Establishment not found"));
		room.setEstablishment(establishment);
		RoomType roomType = roomTypeRepo.findById(roomReqDTO.getRoomTypeId())
				.orElseThrow(() -> new NotFoundException("Room type not found"));
		room.setRoomType(roomType);
		room.setRoomNumber(roomReqDTO.getRoomNumber());
		room.setIncidentActive(roomReqDTO.isIncidentActive());
		room.setClean(roomReqDTO.isClean());
		room.setSupervised(roomReqDTO.isSupervised());
		room.setOccupied(roomReqDTO.isOccupied());
		return room;
	}

}
