package br.com.vikfastfood.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
		classes = ApiApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.NONE
)
class ApiApplicationTests {
	@Test
	void contextLoads() {}
}