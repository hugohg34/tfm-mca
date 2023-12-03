package mca.house_keeping_service.establishment.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import mca.house_keeping_service.BaseTestConfig;
import mca.house_keeping_service.PopulatorDB;
import mca.house_keeping_service.establishment.model.Establishment;
import mca.house_keeping_service.room.model.Room;
import mca.house_keeping_service.room.model.RoomType;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EstablishmentRestRackIntegrationTest extends BaseTestConfig {

	@Autowired
	private PopulatorDB populator;
	private static final String BASE_PATH = "/api/v1/establishments";
	private Establishment establishmentDB;
	private RoomType roomTypeDB;
	private List<Room> rooms;

	@Test
	void getRackOfEstablishmentTest() {
		given()
				.contentType(CONTENT_TYPE)
				.when()
				.get(BASE_PATH + "/{establishmentId}/rooms", establishmentDB.getId().toString())
				.then()
				.statusCode(200)
				.body("[0].name", is(notNullValue()))
				.body("[0].id", is(notNullValue()))
				.body("[0].roomNumber", greaterThan(0))
				.body("[0].roomType", equalTo(roomTypeDB.getName()));
	}

	@Test
	void cleanRoomTest() {
		given()
				.when()
				.put(BASE_PATH + "/{establishmentId}/room/{roomId}/clean",
						establishmentDB.getId().toString(),
						rooms.get(0).getId().toString())
				.then()
				.statusCode(200)
				.body("clean", is(true));
	}

	@Test
	void dirtyRoomTest() {
		given()
				.when()
				.put(BASE_PATH + "/{establishmentId}/room/{roomId}/dirty",
						establishmentDB.getId().toString(),
						rooms.get(0).getId().toString())
				.then()
				.statusCode(200)
				.body("clean", is(false))
				.body("supervised", is(false));
	}

	@Test
	void supervisedRoomTest() {
		given()
				.when()
				.put(BASE_PATH + "/{establishmentId}/room/{roomId}/supervised",
						establishmentDB.getId().toString(),
						rooms.get(0).getId().toString())
				.then()
				.statusCode(200)
				.body("supervised", is(true))
				.body("clean", is(true));
	}

	@BeforeAll
	void populateDB() {
		populator.populate();
		establishmentDB = populator.getEstablishmentDB();
		roomTypeDB = populator.getRoomTypeDB();
		rooms = populator.getRooms();
	}

}
