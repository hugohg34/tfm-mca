package mca.house_keeping_service.room.controller;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import mca.house_keeping_service.room.dto.RoomReqDTO;
import mca.house_keeping_service.room.dto.RoomRespDTO;

@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
public interface RoomRestInterface {

    @Operation(summary = "Get room by id")
    @GetMapping("/{id}")
    RoomRespDTO getRoomById(@PathVariable("id") UUID id);

    @Operation(summary = "Create new room")
    @PostMapping
    RoomRespDTO createRoom(@RequestBody RoomReqDTO room);

    

}
