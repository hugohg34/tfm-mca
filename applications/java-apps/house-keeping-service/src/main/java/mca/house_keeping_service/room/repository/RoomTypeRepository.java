package mca.house_keeping_service.room.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import mca.house_keeping_service.room.model.RoomType;

public interface RoomTypeRepository extends JpaRepository<RoomType, UUID> {

}
