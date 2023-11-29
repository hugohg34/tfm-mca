package mca.house_keeping_service.reservation.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import mca.house_keeping_service.BaseTestConfig;
import mca.house_keeping_service.PopulatorDB;
import mca.house_keeping_service.establishment.model.Establishment;
import mca.house_keeping_service.establishment.model.Guest;
import mca.house_keeping_service.establishment.model.GuestId;
import mca.house_keeping_service.reservation.dto.ReservationReqDTO;
import mca.house_keeping_service.reservation.model.Reservation;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationRestTest extends BaseTestConfig {

	@Autowired
	private PopulatorDB populator;
	private static final String BASE_PATH = "/api/reservation";
	private Reservation reservationDB;
	private Establishment establishmentDB;
	private Guest guestDB;

	@Test
	void reservationCheckinTest() {
		given()
				.contentType(CONTENT_TYPE)
				.body(new GuestId(guestDB.getId()))
				.when()
				.put(BASE_PATH + "/{id}/checkin", reservationDB.getId().toString())
				.then()
				.statusCode(200)
				.body("reservationId", equalTo(reservationDB.getId()));
	}

	@Test
	void addReservationTest() {
		ReservationReqDTO resReqDTO = ReservationReqDTO.builder()
				.checkInDate(LocalDate.now())
				.checkOutDate(LocalDate.now().plusDays(1))
				.reservationName("Test Reservation")
				.actualArrivalTime(LocalDateTime.now())
				.actualDepartureTime(LocalDateTime.now().plusDays(1))
				.establishmentId(establishmentDB.getId())
				.build();
		resReqDTO.addRoomType(populator.getRoomTypeDB().getId(), 2);

		given()
				.contentType(CONTENT_TYPE)
				.body(resReqDTO)
				.when()
				.post(BASE_PATH)
				.then()
				.statusCode(201)
				.body("reservationId", is(notNullValue()));

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

	@BeforeAll
	void populateDB() {
		populator.populate();
		reservationDB = populator.getReservation();
		establishmentDB = populator.getEstablishmentDB();
		guestDB = populator.getGuest();
	}

}
