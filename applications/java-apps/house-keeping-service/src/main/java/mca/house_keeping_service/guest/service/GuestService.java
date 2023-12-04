package mca.house_keeping_service.guest.service;

import org.springframework.stereotype.Service;

import mca.house_keeping_service.guest.dto.GuestReqDTO;
import mca.house_keeping_service.guest.dto.GuestRespDTO;
import mca.house_keeping_service.guest.model.Guest;
import mca.house_keeping_service.guest.model.GuestId;
import mca.house_keeping_service.guest.repository.GuestRepository;
import mca.house_keeping_service.util.NotFoundException;

@Service
public class GuestService {

    private GuestRepository guestRepo;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepo = guestRepository;
    }

    public GuestRespDTO getGuestById(GuestId id) {
        Guest guest = guestRepo.findById(id.getValue())
                .orElseThrow(() -> new NotFoundException("Guest not found"));
        return mapToDTO(guest);
                
    }

    private GuestRespDTO mapToDTO(Guest guest) {
         return new GuestRespDTO(
            guest.getId(),
            guest.getName(),
            guest.getSurname(),
            guest.getSecondSurname(),
            guest.getPhoneNumber(),
            guest.getEmail(),
            guest.getRoomPreference(),
            guest.getComments(),
            guest.getIdNumber()
        );
    }

    public GuestRespDTO createGuest(GuestReqDTO guest) {
        Guest newGuest = Guest.builder()
            .name(guest.getName())
            .surname(guest.getSurname())
            .secondSurname(guest.getSecondSurname())
            .phoneNumber(guest.getPhoneNumber())
            .email(guest.getEmail())
            .roomPreference(guest.getRoomPreference())
            .comments(guest.getComments())
            .idNumber(guest.getIdNumber())
            .build();
        Guest savedGuest = guestRepo.save(newGuest);
        return mapToDTO(savedGuest);
    }

    public GuestRespDTO updateGuest(GuestId id, GuestReqDTO guest) {
        Guest guestToUpdate = guestRepo.findById(id.getValue())
            .orElseThrow(() -> new NotFoundException("Guest not found"));
        guestToUpdate.setName(guest.getName());
        guestToUpdate.setSurname(guest.getSurname());
        guestToUpdate.setSecondSurname(guest.getSecondSurname());
        guestToUpdate.setPhoneNumber(guest.getPhoneNumber());
        guestToUpdate.setEmail(guest.getEmail());
        guestToUpdate.setRoomPreference(guest.getRoomPreference());
        guestToUpdate.setComments(guest.getComments());
        guestToUpdate.setIdNumber(guest.getIdNumber());
        Guest updatedGuest = guestRepo.save(guestToUpdate);
        return mapToDTO(updatedGuest);
    }

    public void deleteGuest(GuestId id) {
        guestRepo.deleteById(id.getValue());
    }

}
