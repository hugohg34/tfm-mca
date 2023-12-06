package mca.house_keeping_service.guest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import mca.house_keeping_service.guest.dto.GuestReqDTO;
import mca.house_keeping_service.guest.dto.GuestRespDTO;
import mca.house_keeping_service.guest.model.GuestId;
import mca.house_keeping_service.guest.service.GuestService;

@RestController
public class GuestRest implements GuestRestInterface {

    private GuestService guestService;

    public GuestRest(GuestService guestService) {
        this.guestService = guestService;
    }

    @Override
    public GuestRespDTO getGuestById(GuestId id) {
        return guestService.getGuestById(id);
    }

    @Override
    public ResponseEntity<GuestRespDTO> createGuest(GuestReqDTO guest) {
        return new ResponseEntity<>(guestService.createGuest(guest),  HttpStatus.CREATED);
    }

    @Override
    public GuestRespDTO updateGuest(GuestId id, GuestReqDTO guest) {
        return guestService.updateGuest(id, guest);    }

    @Override
    public ResponseEntity<Void> deleteGuest(GuestId id) {
        guestService.deleteGuest(id);
        return ResponseEntity.noContent().build();
    }

}
