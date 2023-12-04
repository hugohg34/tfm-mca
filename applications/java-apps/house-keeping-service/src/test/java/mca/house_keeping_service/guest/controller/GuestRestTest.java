package mca.house_keeping_service.guest.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Test;

import io.restassured.module.mockmvc.response.ValidatableMockMvcResponse;
import mca.house_keeping_service.BaseTestConfig;
import mca.house_keeping_service.guest.dto.GuestReqDTO;

class GuestRestTest extends BaseTestConfig {

	private static final String BASE_PATH = "/api/v1/guests";

	@Test
	void createGuestTest() {
		GuestReqDTO guestReq = createGuestReqDTO();
		given()
				.contentType(CONTENT_TYPE)
				.body(guestReq)
				.when()
				.post(BASE_PATH)
				.then()
				.statusCode(200)
				.and()
				.body("id", is(notNullValue()))
				.body("name", equalTo(guestReq.getName()))
				.body("surname", equalTo(guestReq.getSurname()))
				.body("secondSurname", equalTo(guestReq.getSecondSurname()))
				.body("phoneNumber", equalTo(guestReq.getPhoneNumber()))
				.body("email", equalTo(guestReq.getEmail()))
				.body("roomPreference", equalTo(guestReq.getRoomPreference()))
				.body("comments", equalTo(guestReq.getComments()))
				.body("idNumber", equalTo(guestReq.getIdNumber()));

	}
	
	@Test
	void createGuestNotEmailTest() {
		GuestReqDTO guestReq = GuestReqDTO.builder().comments("").build();
		given()
				.contentType(CONTENT_TYPE)
				.body(guestReq)
				.when()
				.post(BASE_PATH)
				.then()
				.statusCode(200);
	}

	@Test
	void getGuestTest() {
		// Arrange
		GuestReqDTO guestReq = createGuestReqDTO();
		ValidatableMockMvcResponse createMvcResp = given()
				.contentType(CONTENT_TYPE)
				.body(guestReq)
				.when()
				.post(BASE_PATH)
				.then()
				.statusCode(200);
		Long guestId = createMvcResp.extract().body().jsonPath().get("id");

		given()
				.contentType(CONTENT_TYPE)
				.when()
				.get(BASE_PATH + "/{id}", guestId.toString())
				.then()
				.statusCode(200);
	}
	
	@Test
	void delteGuestTest() {
		// Arrange
		GuestReqDTO guestReq = createGuestReqDTO();
		ValidatableMockMvcResponse createMvcResp = given()
				.contentType(CONTENT_TYPE)
				.body(guestReq)
				.when()
				.post(BASE_PATH)
				.then()
				.statusCode(200);
		Long guestId = createMvcResp.extract().body().jsonPath().get("id");

		given()
				.contentType(CONTENT_TYPE)
				.when()
				.delete(BASE_PATH + "/{id}", guestId.toString())
				.then()
				.statusCode(204);
	}
	
	@Test
	void updateGuestTest() {
		// Arrange
		GuestReqDTO guestReq = createGuestReqDTO();
		ValidatableMockMvcResponse createMvcResp = given()
				.contentType(CONTENT_TYPE)
				.body(guestReq)
				.when()
				.post(BASE_PATH)
				.then()
				.statusCode(200);
		Long guestId = createMvcResp.extract().body().jsonPath().get("id");
		
		guestReq.setName("New name");

		given()
				.contentType(CONTENT_TYPE)
				.body(guestReq)
				.when()
				.put(BASE_PATH + "/{id}", guestId.toString())
				.then()
				.statusCode(200)
				.body("name", equalTo(guestReq.getName()));
	}

	@Test
	void getGuestInvalidIdTest() {
		given()
				.contentType(CONTENT_TYPE)
				.when()
				.get(BASE_PATH + "/{id}", "1111")
				.then()
				.statusCode(404);
	}

	private GuestReqDTO createGuestReqDTO() {
		return GuestReqDTO.builder()
				.name("NameTest")
				.surname("SurTest")
				.secondSurname("SecondTest")
				.phoneNumber("123456789")
				.email("noname@nodomain.com")
				.roomPreference("No smoking")
				.comments("Guest builded from test")
				.idNumber("12345678A")
				.build();
	}

}
