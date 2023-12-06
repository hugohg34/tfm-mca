package mca.house_keeping_service.establishment.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import mca.house_keeping_service.establishment.dto.EstablishmentReqDTO;
import mca.house_keeping_service.establishment.dto.EstablishmentRespDTO;
import mca.house_keeping_service.establishment.model.Establishment;
import mca.house_keeping_service.establishment.repository.EstablishmentRepository;
import mca.house_keeping_service.util.NotFoundException;

@Service
public class EstablishmentService {

	private final EstablishmentRepository establishmentRepository;

	public EstablishmentService(final EstablishmentRepository establishmentRepository) {
		this.establishmentRepository = establishmentRepository;
	}

	public Page<EstablishmentRespDTO> findAllPaginatedAndSorted(int page, int size, String sortBy) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
		return establishmentRepository.findAll(pageable).map(this::mapToDTO);
	}

	public EstablishmentRespDTO get(final UUID id) {
		return establishmentRepository.findById(id)
				.map(this::mapToDTO)
				.orElseThrow(NotFoundException::new);
	}

	public EstablishmentRespDTO create(final EstablishmentReqDTO establishmentDTO) {
		final Establishment establishment = new Establishment();
		mapToEntity(establishmentDTO, establishment);
		establishmentRepository.save(establishment);
		return mapToDTO(establishment);
	}

	public void update(final UUID id, final EstablishmentReqDTO establishmentDTO) {
		final Establishment establishment = establishmentRepository.findById(id)
				.orElseThrow(NotFoundException::new);
		mapToEntity(establishmentDTO, establishment);
		establishmentRepository.save(establishment);
	}

	public void delete(final UUID id) {
		establishmentRepository.deleteById(id);
	}

	private EstablishmentRespDTO mapToDTO(final Establishment establishment) {
		EstablishmentRespDTO establishmentDTO = new EstablishmentRespDTO();
		establishmentDTO.setId(establishment.getId());
		establishmentDTO.setName(establishment.getName());
		return establishmentDTO;
	}

	private Establishment mapToEntity(EstablishmentReqDTO establishmentDTO,
			Establishment establishment) {
		establishment.setId(establishmentDTO.getId());
		establishment.setName(establishmentDTO.getName());
		return establishment;
	}

}
