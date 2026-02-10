package com.moura.integrationtests.controllers.withXml;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.moura.config.TestConfigs;
import com.moura.integrationtests.dto.UsuarioDTO;
import com.moura.integrationtests.dto.wrappers.xml.WrappersUsuarioDTOXml;
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

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioControllersXmlTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    private static RequestSpecification specification;
    private static XmlMapper objectMapper;
    private static UsuarioDTO usuarioDTO;

    @BeforeAll
    static void setUp() {
        objectMapper = new XmlMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        usuarioDTO = new UsuarioDTO();
    }
    @Test
    @Order(1)
    void createTest() throws IOException {
        mockUsuario();

        specification = new RequestSpecBuilder()
                .setPort(port)
                .setBasePath("/api/usuario/v1")
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_MOURA)

                //.addFilter(new RequestLoggingFilter(LogDetail.ALL))
                  //  .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                    .body(usuarioDTO)
                .when()
                    .post()
                .then()
                    .statusCode(201)
                    .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                    .body()
                        .asString();
        UsuarioDTO createdUsuario = objectMapper.readValue(content, UsuarioDTO.class);
        usuarioDTO = createdUsuario;

        assertNotNull(createdUsuario.getId());

        // Verifica se o ID é maior que zero
        assertTrue(createdUsuario.getId() > 0);

        assertEquals("Alisson TEST", createdUsuario.getNome());
        assertEquals(39, createdUsuario.getIdade());
        assertEquals(76, createdUsuario.getPeso());
        assertEquals(1.69, createdUsuario.getAltura());
        assertTrue(createdUsuario.getEnabled());
    }

    @Test
    @Order(2)
    void  updateTest() throws IOException {
        usuarioDTO.setNome("Moura Arruda");

        var content = given(specification)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                    .body(usuarioDTO)
                .when()
                    .put("{id}", usuarioDTO.getId())
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                    .body()
                        .asString();
        UsuarioDTO createdUsuario = objectMapper.readValue(content, UsuarioDTO.class);
        usuarioDTO = createdUsuario;

        assertNotNull(createdUsuario.getId());

        // Verifica se o ID é maior que zero
        assertTrue(createdUsuario.getId() > 0);

        assertEquals("Moura Arruda", createdUsuario.getNome());
        assertEquals(39, createdUsuario.getIdade());
        assertEquals(76, createdUsuario.getPeso());
        assertEquals(1.69, createdUsuario.getAltura());
        assertTrue(createdUsuario.getEnabled());
    }

    @Test
    @Order(3)
    void findByIdTest() throws IOException {

        var content = given(specification)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .pathParam("id", usuarioDTO.getId())
                .when()
                    .get("{id}")
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                    .body()
                        .asString();

        UsuarioDTO createdUsuario = objectMapper.readValue(content, UsuarioDTO.class);
        usuarioDTO = createdUsuario;

        assertNotNull(createdUsuario.getId());

        // Verifica se o ID é maior que zero
        assertTrue(createdUsuario.getId() > 0);

        assertEquals("Moura Arruda", createdUsuario.getNome());
        assertEquals(39, createdUsuario.getIdade());
        assertEquals(76, createdUsuario.getPeso());
        assertEquals(1.69, createdUsuario.getAltura());
        assertTrue(createdUsuario.getEnabled());
    }

    @Test
    @Order(4)
    void disableTest() throws IOException {

        var content = given(specification)
                    .accept(MediaType.APPLICATION_XML_VALUE)
                    .pathParam("id", usuarioDTO.getId())
                .when()
                    .patch("{id}")
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                    .body()
                        .asString();

        UsuarioDTO createdUsuario = objectMapper.readValue(content, UsuarioDTO.class);
        usuarioDTO = createdUsuario;

        assertNotNull(createdUsuario.getId());

        // Verifica se o ID é maior que zero
        assertTrue(createdUsuario.getId() > 0);

        assertEquals("Moura Arruda", createdUsuario.getNome());
        assertEquals(39, createdUsuario.getIdade());
        assertEquals(76, createdUsuario.getPeso());
        assertEquals(1.69, createdUsuario.getAltura());
        assertFalse(createdUsuario.getEnabled());
    }

    @Test
    @Order(5)
    void deleteTest() throws IOException {

        given(specification)
                    .pathParam("id", usuarioDTO.getId())
                .when()
                    .delete("{id}")
                .then()
                    .statusCode(204);
    }

    @Test
    @Order(6)
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
                .accept(MediaType.APPLICATION_XML_VALUE)
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

    @Test
    @Order(7)
    void findAllTest() throws IOException {

        var content = given(specification)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .queryParams("page", 1, "size", 2, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();

        WrappersUsuarioDTOXml wrapperUsuarioDTO = objectMapper.readValue(content, WrappersUsuarioDTOXml.class);
        List<UsuarioDTO> listaUsuario = wrapperUsuarioDTO.getContent();

        UsuarioDTO usuarioOne = listaUsuario.get(0);
        assertNotNull(usuarioOne.getId());
        // Verifica se o ID é maior que zero
        assertTrue(usuarioOne.getId() > 0);

        assertEquals("Adara", usuarioOne.getNome());
        assertEquals(59, usuarioOne.getIdade());
        assertEquals(74.5, usuarioOne.getPeso());
        assertEquals(1.85, usuarioOne.getAltura());
        assertFalse(usuarioOne.getEnabled());


        UsuarioDTO usuarioTwo = listaUsuario.get(1);
        assertNotNull(usuarioTwo.getId());
        // Verifica se o ID é maior que zero
        assertTrue(usuarioTwo.getId() > 0);

        assertEquals("Ade", usuarioTwo.getNome());
        assertEquals(43, usuarioTwo.getIdade());
        assertEquals(72.6, usuarioTwo.getPeso());
        assertEquals(1.46, usuarioTwo.getAltura());
        assertTrue(usuarioTwo.getEnabled());
    }


    private void mockUsuario() {
        usuarioDTO.setNome("Alisson TEST");
        usuarioDTO.setIdade(39);
        usuarioDTO.setPeso(76);
        usuarioDTO.setAltura(1.69);
        usuarioDTO.setEnabled(true);
    }
}