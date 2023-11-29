package mca.house_keeping_service;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class BaseTestConfig {
	
	@Autowired
	protected WebApplicationContext webApplicationContext;
	
	protected static final String CONTENT_TYPE = "application/json";


	@BeforeEach
	public void setup() {
		RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
	}

}