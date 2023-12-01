package mca.house_keeping_service.establishment.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.*;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import mca.house_keeping_service.BaseTestConfig;
import mca.house_keeping_service.PopulatorDB;
import mca.house_keeping_service.establishment.dto.EstablishmentReqDTO;
import mca.house_keeping_service.establishment.model.Establishment;
import mca.house_keeping_service.room.model.RoomType;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EstablishmentRestIntegrationTest extends BaseTestConfig {

	@Autowired
	private PopulatorDB populator;
	private static final String BASE_PATH = "/api/v1/establishments";
	private Establishment establishmentDB;
	private RoomType roomTypeDB;

	@Test
	void getAllEstablishmentsTest() {
		given()
				.when()
				.get(BASE_PATH)
				.then()
				.statusCode(200)
				.body("content", hasSize(greaterThanOrEqualTo(0)));
	}

	@Test
	void createEstablishmentTest() {
		EstablishmentReqDTO estReq = new EstablishmentReqDTO(UUID.randomUUID(), "Test estab");

		given()
				.contentType(CONTENT_TYPE)
				.body(estReq)
				.when()
				.post(BASE_PATH)
				.then()
				.statusCode(201)
				.and()
				.body(containsString(estReq.getId().toString()));
	}

	@Test
	void getEstablishmentTest() {
		given()
				.contentType(CONTENT_TYPE)
				.when()
				.get(BASE_PATH + "/{id}", establishmentDB.getId().toString())
				.then()
				.statusCode(200)
				.and()
				.body("id", equalTo(establishmentDB.getId().toString()))
				.and()
				.body("name", equalTo(establishmentDB.getName()));
	}

	@Test
	void updateEstablishmentTest() {
		// Arrenge
		EstablishmentReqDTO estReq = new EstablishmentReqDTO(UUID.randomUUID(), "Test estab");
		createEstablishment(estReq);

		estReq.setName("Establishment new name");
		given()
				.contentType(CONTENT_TYPE)
				.body(estReq)
				.when()
				.put(BASE_PATH + "/{id}", estReq.getId().toString())
				.then()
				.statusCode(200);
	}

	private void createEstablishment(EstablishmentReqDTO estReq) {
		given()
				.contentType(CONTENT_TYPE)
				.body(estReq)
				.when()
				.post(BASE_PATH)
				.then()
				.statusCode(201);
	}

	@Test
	void deleteEstablishmentTest() {
		// Arrenge
		EstablishmentReqDTO estReq = new EstablishmentReqDTO(UUID.randomUUID(), "Test estab");
		createEstablishment(estReq);

		given()
				.contentType(CONTENT_TYPE)
				.when()
				.delete(BASE_PATH + "/{id}", estReq.getId().toString())
				.then()
				.statusCode(204);

		given()
				.when()
				.get(BASE_PATH + "/{id}", estReq.getId().toString())
				.then()
				.statusCode(404);
	}

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
	void getRoomTypesOfEstablishmentTest() {
		given()
				.when()
				.get(BASE_PATH + "/{establishmentId}/room-types", establishmentDB.getId().toString())
				.then()
				.statusCode(200)
				.body("[0].name", is(notNullValue()))
				.body("[0].id",is(notNullValue()))
				.body("[0].bedType", is(notNullValue()))
				.body("[0].description", is(notNullValue()))
				.body("[0].guestCapacity", is(notNullValue()))
				.body("[0].numberOfRooms", is(notNullValue()));
	}
	
	@BeforeAll
	void populateDB() {
		populator.populate();
		establishmentDB = populator.getEstablishmentDB();
		roomTypeDB = populator.getRoomTypeDB();
	}

}
