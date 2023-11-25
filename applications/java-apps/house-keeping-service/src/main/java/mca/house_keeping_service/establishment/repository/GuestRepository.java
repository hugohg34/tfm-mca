package mca.house_keeping_service.establishment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mca.house_keeping_service.establishment.model.Guest;

public interface GuestRepository extends JpaRepository<Guest, Long> {

}
