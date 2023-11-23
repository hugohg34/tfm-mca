package mca.house_keeping_service.establishment.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import mca.house_keeping_service.establishment.dto.EstablishmentDTO;
import mca.house_keeping_service.establishment.service.EstablishmentService;
import mca.house_keeping_service.util.PageSizeTooLargeException;

@RestController
public class EstablishmentRest implements EstablishmentRestInterface {

	private final EstablishmentService establishmentService;
	private static final int MAX_PAGE_SIZE = 50;

	public EstablishmentRest(final EstablishmentService establishmentService) {
		this.establishmentService = establishmentService;
	}

	@Override
	public ResponseEntity<Page<EstablishmentDTO>> getAllEstablishments(
			int page, int size,	String sortBy) {
		if (size > MAX_PAGE_SIZE) {
			throw new PageSizeTooLargeException("Maximum page size allowed is " + MAX_PAGE_SIZE);
		}
		Page<EstablishmentDTO> resultPage = establishmentService.findAllPaginatedAndSorted(page, size, sortBy);
		return ResponseEntity.ok(resultPage);
	}

	@Override
	public ResponseEntity<EstablishmentDTO> getEstablishment(
			final UUID id) {
		return ResponseEntity.ok(establishmentService.get(id));
	}

	@Override
	public ResponseEntity<UUID> createEstablishment(
			final EstablishmentDTO establishmentDTO) {
		final UUID createdId = establishmentService.create(establishmentDTO);
		return new ResponseEntity<>(createdId, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<UUID> updateEstablishment(final UUID id,
			final EstablishmentDTO establishmentDTO) {
		establishmentService.update(id, establishmentDTO);
		return ResponseEntity.ok(id);
	}

	@Override
	public ResponseEntity<Void> deleteEstablishment(final UUID id) {
		establishmentService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
