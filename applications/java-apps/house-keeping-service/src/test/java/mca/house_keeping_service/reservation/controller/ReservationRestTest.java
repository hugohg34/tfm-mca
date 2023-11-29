package mca.house_keeping_service.reservation.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import mca.house_keeping_service.BaseTestConfig;
import mca.house_keeping_service.PopulatorDB;
import mca.house_keeping_service.reservation.model.Reservation;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationRestTest extends BaseTestConfig {

	@Autowired
	private PopulatorDB populator;
	private static final String BASE_PATH = "/api/reservation";
	private Reservation reservation;
/*
	@Test
	void checkinReservationTest() {
		// put /api/reservation/{id}/checkin
		fail("Not yet implemented");
	}
*/
	@Test
	void addReservationTest() {
		given()
				.contentType(CONTENT_TYPE)
				.body(reservation)
				.when()
				.post(BASE_PATH)
				.then()
				.statusCode(201)
				.body("id", is(notNullValue()))
				.body("checkin", equalTo(populator.getReservation().getCheckInDate().toString()))
				.body("checkout", equalTo(populator.getReservation().getCheckOutDate().toString()))
				.body("reservationName", equalTo(populator.getReservation().getReservationName()))
				.body("establishment", equalTo(populator.getReservation().getEstablishment()));
	}

	@Test
	void getReservationTest() {
		given()
				.contentType(CONTENT_TYPE)
				.when()
				.get(BASE_PATH + "/" + reservation.getId().toString())
				.then()
				.statusCode(200)
				.body("id", equalTo(reservation.getId().toString()))
				.body("checkin", equalTo(reservation.getCheckInDate().toString()))
				.body("checkout", equalTo(reservation.getCheckOutDate().toString()))
				.body("reservationName", equalTo(reservation.getReservationName()))
				.body("establishment", equalTo(reservation.getEstablishment().getId().toString()));
	}

	@BeforeAll
	void populateDB() {
		populator.populate();
		reservation = populator.getReservation();
	}

}
