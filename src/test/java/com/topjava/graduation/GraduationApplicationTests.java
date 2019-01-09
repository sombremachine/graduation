package com.topjava.graduation;

import com.topjava.graduation.data.Role;
import com.topjava.graduation.data.UserRepository;
import com.topjava.graduation.service.request.RequestUserTo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.withArgs;
import static org.hamcrest.Matchers.*;
//import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class GraduationApplicationTests {
	@Autowired
	UserRepository userRepository;

	@LocalServerPort
	int serverPort;

	@Test
	public void contextLoads() {
	}

	@Before
	public void initRestAssured() {
		RestAssured.port = serverPort;
		RestAssured.baseURI = "http://localhost";
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}
//		SessionFilter sessionFilter = new SessionFilter();


    public void getAllRestaurantsBy(String email, String password){
        given()
                .auth()
                .basic(email, password).
        when().
                get("/restaurant").
        then().
                statusCode(HttpStatus.SC_OK)
                .and()
                .assertThat()
                .body(
                        "size()", is(3),
                        "id", is(not(empty())),
                        "name", hasItems("restr_1", "restr_2","restr_3"))
                .and()
                .assertThat()
                .root("find {it.name == '%s'}")
                .body("numVotes", withArgs("restr_1"), is(0))
                .body("numVotes", withArgs("restr_2"), is(2))
                .body("numVotes", withArgs("restr_3"), is(0))
                .body("menu.name", withArgs("restr_2"), hasItems("menu_item_today_0", "menu_item_today_1"))
                .body("menu.name", withArgs("restr_2"), not(hasItems("menu_item_yestarday_0")))
        ;

    }

	@Test
	public void getAllRestaurantsRoot(){
        getAllRestaurantsBy("root@gmail.com", "root");
	}

    @Test
    public void getAllRestaurantsAdmin(){
        getAllRestaurantsBy("admin@gmail.com", "admin");
    }

    @Test
	public void getAllRestaurantsUser(){
        getAllRestaurantsBy("user@yandex.ru", "password");
	}

    public void registerSomeUser(String email, String password, Role role){
        RequestUserTo requestUserTo = new RequestUserTo();
        requestUserTo.setName("testuser");
        requestUserTo.setPassword(password);
        requestUserTo.setEmail(email);
        requestUserTo.setUserRole(role);

        given()
                .contentType(ContentType.JSON)
                .body(requestUserTo).
        when().
                post("/rest/profile/register").
        then().
                statusCode(HttpStatus.SC_CREATED)
                .and()
                .assertThat()
                .body(
                        "id", is(not(empty())),
                        "name", is("testuser"),
                        "email", is(email)
                );

        getAllRestaurantsBy(email,password);
    }

    @Test
    public void registerAdmin(){
	    registerSomeUser("testadmin@gmail.com", "admin", Role.ROLE_ADMIN);
    }

    @Test
    public void registerUser(){
        registerSomeUser("testuser@yandex.ru", "password",null);
    }

    public void someUserVote(String email, String password, Integer restaurantId){
        given().auth().basic(email, password).get("/restaurant").prettyPrint();
        Integer numVotesBefore = given()
                    .auth()
                    .basic(email, password)
                    .get("/restaurant").jsonPath().getInt("find { it.id == '"+restaurantId+"' }.numVotes");

        given()
                .auth()
                .basic(email, password).
        when().
                post("/user/vote/{id}", restaurantId).
        then().
                statusCode(HttpStatus.SC_OK);

        given().auth().basic(email, password).get("/restaurant").prettyPrint();

        given()
                .auth()
                .basic(email, password).
        when().
                get("/restaurant").
        then().
                statusCode(HttpStatus.SC_OK)
                .and()
                .assertThat()
                .root("find {it.id == %d}")
                .body("numVotes", withArgs(restaurantId), is(numVotesBefore + 1))

        ;
    }

    @Test
    public void voteUser(){
        String email = "user@yandex.ru";
        String password = "password";
        List<Integer> ids = given().auth().basic(email, password).get("/restaurant").path("id");
        someUserVote(email, password, ids.get(0));
    }

    @Test
    public void voteAdmin(){
        String email = "admin@gmail.com";
        String password = "admin";
        List<Integer> ids = given().auth().basic(email, password).get("/restaurant").path("id");
        someUserVote(email, password, ids.get(0));
    }
}

