package mca.house_keeping_service.reservation.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.module.mockmvc.response.ValidatableMockMvcResponse;
import mca.house_keeping_service.BaseTestConfig;
import mca.house_keeping_service.PopulatorDB;
import mca.house_keeping_service.establishment.model.Establishment;
import mca.house_keeping_service.establishment.model.Guest;
import mca.house_keeping_service.establishment.model.GuestId;
import mca.house_keeping_service.reservation.dto.ReservationReqDTO;
import mca.house_keeping_service.reservation.model.Reservation;
import mca.house_keeping_service.room.model.Room;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationRestIntegrationTest extends BaseTestConfig {

	@Autowired
	private PopulatorDB populator;
	private static final String BASE_PATH = "/api/v1/reservation";
	private Reservation reservationDB;
	private Establishment establishmentDB;
	private Guest guestDB;
	private List<Room> roomsDB;

	@Test
	void addReservationTest() {
		MockMvcResponse mvcResponse = createReservation();
		mvcResponse
				.then()
				.statusCode(201)
				.body("reservationId", is(notNullValue()));
	}

	@Test
	void addRoomToReservationTest() {
		List<String> roomsId = new ArrayList<>();
		roomsId.add(roomsDB.get(0).getId().toString());
		roomsId.add(roomsDB.get(1).getId().toString());

		given()
				.contentType(CONTENT_TYPE)
				.body(roomsId)
				.when()
				.post(BASE_PATH + "/{reservationId}/rooms", reservationDB.getId().toString())
				.then()
				.statusCode(204);
	}

	@Test
	void addRoomToNonexistentReservationTest() {
		String nonexistentReservationId = "1234";
		List<String> dummyRoomIds = new ArrayList<>();
		dummyRoomIds.add(roomsDB.get(0).getId().toString());

		given()
				.contentType(CONTENT_TYPE)
				.body(dummyRoomIds)
				.when()
				.post(BASE_PATH + "/{reservationId}/rooms", nonexistentReservationId)
				.then()
				.statusCode(404);
	}

	@Test
	void addReservationWithIncompleteDataTest() {
		ReservationReqDTO incompleteResReqDTO = ReservationReqDTO.builder()
				.checkInDate(LocalDate.now())
				.reservationName("Test Reservation")
				.actualArrivalTime(LocalDateTime.now())
				.build();

		given()
				.contentType(CONTENT_TYPE)
				.body(incompleteResReqDTO)
				.when()
				.post(BASE_PATH)
				.then()
				.statusCode(400);
	}

	@Test
	void getReservationTest() {
		given()
				.contentType(CONTENT_TYPE)
				.when()
				.get(BASE_PATH + "/" + reservationDB.getId())
				.then()
				.statusCode(200)
				.body("id", equalTo(reservationDB.getId()))
				.body("checkInDate", equalTo(reservationDB.getCheckInDate().toString()))
				.body("checkOutDate", equalTo(reservationDB.getCheckOutDate().toString()))
				.body("reservationName", equalTo(reservationDB.getReservationName()))
				.body("establishmentId", equalTo(reservationDB.getEstablishment().getId().toString()));
	}

	@Test
	void getNonexistentReservationDetailsTest() {
		String nonexistentReservationId = "1234";

		given()
				.contentType(CONTENT_TYPE)
				.when()
				.get(BASE_PATH + "/" + nonexistentReservationId)
				.then()
				.statusCode(404);
	}

	@Test
	void reservationCheckinTest() {
		MockMvcResponse mvcResponse = reservationCheckin(reservationDB.getId().toString(),
				new GuestId(guestDB.getId()));
		mvcResponse
				.then()
				.statusCode(200)
				.body("reservationId", equalTo(reservationDB.getId()));
	}

	@Test
	void checkinWithNonexistentReservationIdTest() {
		String nonexistentReservationId = "1254";
		GuestId dummyGuestId = new GuestId();

		MockMvcResponse mvcResponse = reservationCheckin(nonexistentReservationId, dummyGuestId);
		mvcResponse
				.then()
				.statusCode(404);
	}

	private MockMvcResponse reservationCheckin(String reservationInDB, GuestId guestInDB) {
		return given()
				.contentType(CONTENT_TYPE)
				.body(guestInDB)
				.when()
				.put(BASE_PATH + "/{id}/checkin", reservationInDB);
	}

	@Test
	void reservationCheckoutTest() {
		// arange
		MockMvcResponse mvcResponse = createReservation();
		ValidatableMockMvcResponse createReservRespo = mvcResponse
				.then()
				.statusCode(201)
				.body("reservationId", is(notNullValue()));
		
		String createdReservIdStr = createReservRespo.extract().body().jsonPath().getString("reservationId");

		reservationCheckin(createdReservIdStr, new GuestId(guestDB.getId()))
				.then()
				.statusCode(200);
		
		given()
				.contentType(CONTENT_TYPE)
				.when()
				.put(BASE_PATH + "/{id}/checkout", createdReservIdStr)
				.then()
				.statusCode(200)
				.body("reservationId", equalTo(Long.parseLong(createdReservIdStr)));
	}

	private MockMvcResponse createReservation() {
		ReservationReqDTO resReqDTO = ReservationReqDTO.builder()
				.checkInDate(LocalDate.now())
				.checkOutDate(LocalDate.now().plusDays(1))
				.reservationName("Test Reservation")
				.actualArrivalTime(LocalDateTime.now())
				.actualDepartureTime(LocalDateTime.now().plusDays(1))
				.establishmentId(establishmentDB.getId())
				.build();
		resReqDTO.addRoomType(populator.getRoomTypeDB().getId(), 2);

		return given()
				.contentType(CONTENT_TYPE)
				.body(resReqDTO)
				.when()
				.post(BASE_PATH);
	}

	@BeforeAll
	void populateDB() {
		populator.populate();
		reservationDB = populator.getReservation();
		establishmentDB = populator.getEstablishmentDB();
		guestDB = populator.getGuest();
		roomsDB = populator.getRooms();

	}
}
