package mca.house_keeping_service.establishment.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.*;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import mca.house_keeping_service.BaseTestConfig;
import mca.house_keeping_service.establishment.dto.EstablishmentReqDTO;

class EstablishmentRestIntegrationTest extends BaseTestConfig {

	private static final String BASE_PATH = "/api/establishments";
	private UUID estabUUID = UUID.fromString("00000000-0000-0000-0000-000000000001");

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
		EstablishmentReqDTO estReq = new EstablishmentReqDTO(estabUUID, "Test estab");

		given()
				.contentType(CONTENT_TYPE)
				.body(estReq)
				.when()
				.post(BASE_PATH)
				.then()
				.statusCode(201)
				.and()
				.body(containsString(estabUUID.toString()));
	}

	@Test
	void getEstablishmentTest() {
		// Arrange
		EstablishmentReqDTO estReq = new EstablishmentReqDTO(estabUUID, "Test estab");
		createEstablishment(estReq);

		given()
				.contentType(CONTENT_TYPE)
				.when()
				.get(BASE_PATH + "/{id}", estReq.getId().toString())
				.then()
				.statusCode(200)
				.and()
				.body("id", equalTo(estReq.getId().toString()))
				.and()
				.body("name", equalTo(estReq.getName()));
	}

	@Test
	void updateEstablishmentTest() {
		// Arrenge
		EstablishmentReqDTO estReq = new EstablishmentReqDTO(estabUUID, "Test estab");
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
		given()
				.when()
				.delete(BASE_PATH + "/{id}", estabUUID.toString())
				.then()
				.statusCode(204);

		given()
				.when()
				.get(BASE_PATH + "/{id}", estabUUID.toString())
				.then()
				.statusCode(404);
	}

}
