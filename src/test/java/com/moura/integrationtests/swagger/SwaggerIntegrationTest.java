package com.moura.integrationtests.swagger;

import com.moura.config.TestConfigs;
import com.moura.integrationtests.testcontainers.AbstractIntegrationTest;
import io.swagger.v3.oas.annotations.media.Content;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
	properties = {"server.port=8888"})
class SwaggerIntegrationTest extends AbstractIntegrationTest {

	private static final int SWAGGER_PORT = 8888;

	@Test
	void shouldDisplaySwaggerUIPage() {
		var content = given()
				.basePath("/swagger-ui/index.html")
					.port(SWAGGER_PORT)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		assertTrue(content.contains("Swagger UI"));
	}

}
