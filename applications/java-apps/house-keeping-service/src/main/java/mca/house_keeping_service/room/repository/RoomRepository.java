package mca.house_keeping_service.room.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import mca.house_keeping_service.establishment.model.Establishment;
import mca.house_keeping_service.room.model.Room;


public interface RoomRepository extends JpaRepository<Room, UUID> {

    Room findFirstByEstablishment(Establishment establishment);

	@EntityGraph(attributePaths = {"roomType"})
	List<Room> findByEstablishmentId(UUID establishmentId);

}
