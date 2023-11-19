package mca.house_keeping_service.establishment.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import mca.house_keeping_service.establishment.dto.EstablishmentDTO;
import mca.house_keeping_service.establishment.model.Establishment;
import mca.house_keeping_service.establishment.repository.EstablishmentRepository;
import mca.house_keeping_service.room.repository.RoomRepository;
import mca.house_keeping_service.util.NotFoundException;


@Service
public class EstablishmentService {

    private final EstablishmentRepository establishmentRepository;

    public EstablishmentService(final EstablishmentRepository establishmentRepository,
            final RoomRepository roomRepository) {
        this.establishmentRepository = establishmentRepository;
    }
    
    public Page<EstablishmentDTO> findAllPaginatedAndSorted(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return establishmentRepository.findAll(pageable).map(this::convertToDto);
    }
    
    private EstablishmentDTO convertToDto(Establishment establishment) {
        EstablishmentDTO establishmentDTO = new EstablishmentDTO();
        establishmentDTO.setId(establishment.getId());
        establishmentDTO.setName(establishment.getName());
        return establishmentDTO;
    }

    public EstablishmentDTO get(final UUID id) {
        return establishmentRepository.findById(id)
                .map(establishment -> mapToDTO(establishment, new EstablishmentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final EstablishmentDTO establishmentDTO) {
        final Establishment establishment = new Establishment();
        mapToEntity(establishmentDTO, establishment);
        return establishmentRepository.save(establishment).getId();
    }

    public void update(final UUID id, final EstablishmentDTO establishmentDTO) {
        final Establishment establishment = establishmentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(establishmentDTO, establishment);
        establishmentRepository.save(establishment);
    }

    public void delete(final UUID id) {
        establishmentRepository.deleteById(id);
    }

    private EstablishmentDTO mapToDTO(final Establishment establishment,
            final EstablishmentDTO establishmentDTO) {
        establishmentDTO.setId(establishment.getId());
        establishmentDTO.setName(establishment.getName());
        return establishmentDTO;
    }

    private Establishment mapToEntity(final EstablishmentDTO establishmentDTO,
            final Establishment establishment) {
        establishment.setName(establishmentDTO.getName());
        return establishment;
    }

}
