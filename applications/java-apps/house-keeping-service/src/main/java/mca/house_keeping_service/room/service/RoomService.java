package mca.house_keeping_service.room.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import mca.house_keeping_service.establishment.model.Establishment;
import mca.house_keeping_service.establishment.repository.EstablishmentRepository;
import mca.house_keeping_service.room.dto.RoomDTO;
import mca.house_keeping_service.room.dto.RoomRackDTO;
import mca.house_keeping_service.room.model.Room;
import mca.house_keeping_service.room.repository.RoomRepository;
import mca.house_keeping_service.util.NotFoundException;


@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final EstablishmentRepository establishmentRepository;

    public RoomService(final RoomRepository roomRepository,
            final EstablishmentRepository establishmentRepository) {
        this.roomRepository = roomRepository;
        this.establishmentRepository = establishmentRepository;
    }


	public List<RoomRackDTO> gerRackOf(UUID establishmentId) {
        List<Room> rooms = roomRepository.findByEstablishmentId(establishmentId);
        return rooms.stream().map(this::mapToDTO).collect(Collectors.toList());
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
	
    /**
     * @deprecated(Change for pageable)
     */
    @Deprecated(forRemoval = true)
    public List<RoomDTO> findAll() {
        final List<Room> rooms = roomRepository.findAll(Sort.by("id"));
        return rooms.stream()
                .map(room -> mapToDTO(room, new RoomDTO()))
                .toList();
    }

    public RoomDTO get(final UUID id) {
        return roomRepository.findById(id)
                .map(room -> mapToDTO(room, new RoomDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final RoomDTO roomDTO) {
        final Room room = new Room();
        mapToEntity(roomDTO, room);
        return roomRepository.save(room).getId();
    }

    public void update(final UUID id, final RoomDTO roomDTO) {
        final Room room = roomRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(roomDTO, room);
        roomRepository.save(room);
    }

    public void delete(final UUID id) {
        roomRepository.deleteById(id);
    }

    private RoomDTO mapToDTO(final Room room, final RoomDTO roomDTO) {
        roomDTO.setId(room.getId());
        roomDTO.setName(room.getName());
        roomDTO.setEstablishment(room.getEstablishment() == null ? null : room.getEstablishment().getId());
        return roomDTO;
    }

    private Room mapToEntity(final RoomDTO roomDTO, final Room room) {
        room.setName(roomDTO.getName());
        final Establishment establishment = roomDTO.getEstablishment() == null ? null : establishmentRepository.findById(roomDTO.getEstablishment())
                .orElseThrow(() -> new NotFoundException("establishment not found"));
        room.setEstablishment(establishment);
        return room;
    }

}
