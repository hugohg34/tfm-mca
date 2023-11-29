package mca.house_keeping_service.room.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import mca.house_keeping_service.BaseTestConfig;
import mca.house_keeping_service.PopulatorDB;
import mca.house_keeping_service.establishment.model.Establishment;
import mca.house_keeping_service.reservation.model.Reservation;
import mca.house_keeping_service.room.model.Room;
import mca.house_keeping_service.room.model.RoomType;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoomRackControllerTest extends BaseTestConfig {

	@Autowired
	private PopulatorDB populator;
	private static final String BASE_PATH = "/api/rooms";
	private Establishment establishmentDB;
	private RoomType roomTypeDB;
	private List<Room> rooms;
	private Reservation reservation;

	@Test
	void testGetAllRooms() {
		given()
				.contentType(CONTENT_TYPE)
				.when()
				.get(BASE_PATH + "/{id}", establishmentDB.getId().toString())
				.then()
				.statusCode(200)
				.body("[0].name", is(notNullValue()))
				.body("[0].id", is(notNullValue()))
				.body("[0].roomNumber", greaterThan(0))
				.body("[0].roomType", equalTo(roomTypeDB.getName()));
	}

	@Test
	void addRoomToReservationTest() {
		List<String> roomsId = new ArrayList<>();
		roomsId.add(rooms.get(0).getId().toString());
		roomsId.add(rooms.get(1).getId().toString());

		given()
				.contentType(CONTENT_TYPE)
				.body(roomsId)
				.when()
				.put(BASE_PATH + "/addRooms/{id}", reservation.getId().toString())
				.then()
				.statusCode(204);
	}

	@Test
	void getRoomTypesTest() {
		given()
				.when()
				.get(BASE_PATH + "/types/{id}", establishmentDB.getId().toString())
				.then()
				.statusCode(200)
				.body("[0].name", equalTo(roomTypeDB.getName()))
				.body("[0].id", equalTo(roomTypeDB.getId().toString()))
				.body("[0].bedType", equalTo(roomTypeDB.getBedType()))
				.body("[0].description", equalTo(roomTypeDB.getDescription()))
				.body("[0].guestCapacity", equalTo(roomTypeDB.getGuestCapacity()))
				.body("[0].numberOfRooms", equalTo(roomTypeDB.getNumberOfRooms()));
	}

	@BeforeAll
	void populateDB() {
		populator.populate();
		establishmentDB = populator.getEstablishmentDB();
		roomTypeDB = populator.getRoomTypeDB();
		rooms = populator.getRooms();
		reservation = populator.getReservation();
	}

}
