package com.moura.integrationtests.controllers.withjson;

import com.moura.config.TestConfigs;
import com.moura.integrationtests.dto.UsuarioDTO;
import com.moura.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioControllersTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static UsuarioDTO usuarioDTO;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        usuarioDTO = new UsuarioDTO();
    }
    @Test
    @Order(1)
    void create() throws IOException {
        mockUsuario();

        specification = new RequestSpecBuilder()
                .setPort(port)
                .setBasePath("/api/usuario/v1")
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_MOURA)

                //.addFilter(new RequestLoggingFilter(LogDetail.ALL))
                  //  .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(usuarioDTO)
                .when()
                    .post()
                .then()
                    .statusCode(201)
                .extract()
                    .body()
                        .asString();
        UsuarioDTO createdUsuario = objectMapper.readValue(content, UsuarioDTO.class);
        usuarioDTO = createdUsuario;

        assertNotNull(createdUsuario.getId());
        assertNotNull(createdUsuario.getNome());
        assertNotNull(createdUsuario.getIdade());
        assertNotNull(createdUsuario.getPeso());
        assertNotNull(createdUsuario.getAltura());

        // Verifica se o ID é maior que zero
        assertTrue(createdUsuario.getId() > 0);

        assertEquals("Alisson CORS", createdUsuario.getNome());
        assertEquals(39, createdUsuario.getIdade());
        assertEquals(76, createdUsuario.getPeso());
        assertEquals(1.69, createdUsuario.getAltura());

    }

    @Test
    @Order(2)
    void createWithWrongOrigin() throws IOException {

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_TEST_CORS)
                .setBasePath("/api/usuario/v1")
                .setPort(port)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(usuarioDTO)
                .when()
                .post()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);

    }

    @Test
    @Order(3)
    void findById() throws IOException {

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
                .setBasePath("/api/usuario/v1")
                .setPort(port)
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                    .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", usuarioDTO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        UsuarioDTO createdUsuario = objectMapper.readValue(content, UsuarioDTO.class);
        usuarioDTO = createdUsuario;

        assertNotNull(createdUsuario.getId());
        assertNotNull(createdUsuario.getNome());
        assertNotNull(createdUsuario.getIdade());
        assertNotNull(createdUsuario.getPeso());
        assertNotNull(createdUsuario.getAltura());

        // Verifica se o ID é maior que zero
        assertTrue(createdUsuario.getId() > 0);

        assertEquals("Alisson CORS", createdUsuario.getNome());
        assertEquals(39, createdUsuario.getIdade());
        assertEquals(76, createdUsuario.getPeso());
        assertEquals(1.69, createdUsuario.getAltura());
    }


    @Test
    @Order(4)
    void findByIdWithWrongOrigin() throws IOException {

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_TEST_CORS)
                .setBasePath("/api/usuario/v1")
                .setPort(port)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", usuarioDTO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);
    }

    private void mockUsuario() {
        usuarioDTO.setNome("Alisson CORS");
        usuarioDTO.setIdade(39);
        usuarioDTO.setPeso(76);
        usuarioDTO.setAltura(1.69);
    }
}