package mca.house_keeping_service.guest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mca.house_keeping_service.guest.model.Guest;

public interface GuestRepository extends JpaRepository<Guest, Long> {

}
