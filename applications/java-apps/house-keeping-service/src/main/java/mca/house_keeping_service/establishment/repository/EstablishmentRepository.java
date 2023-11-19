package mca.house_keeping_service.establishment.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import mca.house_keeping_service.establishment.model.Establishment;

public interface EstablishmentRepository extends JpaRepository<Establishment, UUID> {
}
