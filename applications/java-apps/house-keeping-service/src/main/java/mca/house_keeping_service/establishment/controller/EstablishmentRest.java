package mca.house_keeping_service.establishment.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import mca.house_keeping_service.establishment.dto.EstablishmentDTO;
import mca.house_keeping_service.establishment.service.EstablishmentService;
import mca.house_keeping_service.util.PageSizeTooLargeException;


@RestController
@RequestMapping(value = "/api/establishments", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstablishmentRest {

    private final EstablishmentService establishmentService;
    private static final int MAX_PAGE_SIZE = 50;


    public EstablishmentRest(final EstablishmentService establishmentService) {
        this.establishmentService = establishmentService;
    }

    @GetMapping
    public ResponseEntity<Page<EstablishmentDTO>> getAllEstablishments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
    	if (size > MAX_PAGE_SIZE) {
            throw new PageSizeTooLargeException("Maximum page size allowed is " + MAX_PAGE_SIZE);
        }
        Page<EstablishmentDTO> resultPage = establishmentService.findAllPaginatedAndSorted(page, size, sortBy);
        return ResponseEntity.ok(resultPage);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<EstablishmentDTO> getEstablishment(
            @PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(establishmentService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createEstablishment(
            @RequestBody @Valid final EstablishmentDTO establishmentDTO) {
        final UUID createdId = establishmentService.create(establishmentDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateEstablishment(@PathVariable(name = "id") final UUID id,
            @RequestBody @Valid final EstablishmentDTO establishmentDTO) {
        establishmentService.update(id, establishmentDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEstablishment(@PathVariable(name = "id") final UUID id) {
        establishmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
