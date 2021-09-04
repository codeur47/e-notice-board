package com.yorosoft.enoticeboard.controller;

import com.google.gson.JsonObject;
import com.yorosoft.enoticeboard.config.TestDataProvider;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import static com.yorosoft.enoticeboard.config.CrudControllerAPIPath.*;

@DisplayName("Integration Tests of the Author CRUD REST endpoints")
public class AuthorControllerITCase extends CrudControllerITCase {

    static final String CONTENT_TYPE = "application/json";

    @Test
    @DisplayName("GET a list with 5 Authors")
    public void whenGETfindAll_thenGetListOf5Authors() {
        //when
        ValidatableResponse response = given()
                .when()
                .get( baseURL + AUTHOR_BASE_URL + "/")

                .prettyPeek()
                .then();

        //then
        response.statusCode(HttpStatus.OK.value())
                .contentType(CONTENT_TYPE)
                .body("size()", greaterThanOrEqualTo(2));
    }

    @Test
    @DisplayName("GET an Author by Id")
    public void givenAuthorId_thenGetSingleAuthor() {
        //given
        Long authorId = 2L;

        //when
        ValidatableResponse response = given()
                .when()
                .get(baseURL + AUTHOR_BASE_URL + "/" + authorId)

                .prettyPeek()
                .then();

        //then
        response.statusCode(HttpStatus.OK.value())
                .contentType(CONTENT_TYPE)
                .body("id", equalTo(2))
                .body("firstName", equalTo("Jane"))
                .body("lastName", equalTo("Doe"));
    }

    @Test
    @DisplayName("POST an Author to create it")
    public void givenAuthor_whenPOSTSave_thenGetSavedAuthor(){
        //given
        JsonObject authorJson = TestDataProvider.getAuthorJson();

        //when
        ValidatableResponse response = given()
                .contentType(CONTENT_TYPE)
                .body(authorJson.toString())

                .when()
                .post(baseURL + AUTHOR_BASE_URL + "/")

                .prettyPeek()
                .then();

        //then
        response.statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .body("firstName", equalTo("John"))
                .body("lastName", equalTo("Snow"));
    }

    @Test
    @DisplayName("DELETE an Author by Id")
    public void givenAuthorId_whenDELETEbyId_thenAuthorIsDeleted() {
        //given
        Long authorId = 3L;

        //when
        ValidatableResponse response = given()
                .contentType(CONTENT_TYPE)

                .when()
                .delete(baseURL + AUTHOR_BASE_URL + "/" + authorId)

                .prettyPeek()
                .then();

        response.statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("PUT an Author by Id to update it")
    public void givenIdAndUpdatedAuthor_whenPUTUpdate_thenAuthorIsUpdated() {
        //given
        Long authorId = 3L;
        JsonObject authorJson = TestDataProvider.getAuthorJson();

        //when
        ValidatableResponse response = given()
                .contentType(CONTENT_TYPE)
                .body(authorJson.toString())

                .when()
                .put(baseURL + AUTHOR_BASE_URL + "/" + authorId)

                .prettyPeek()
                .then();

        response.statusCode(HttpStatus.OK.value());
    }
}
