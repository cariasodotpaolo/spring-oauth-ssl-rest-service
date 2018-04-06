package org.test.unittest;

import static com.jayway.restassured.RestAssured.given;

import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.jayway.restassured.response.ValidatableResponse;
import mp.cariaso.hr.domain.Employee;

public class TestEmployeeRestService {

	@Test
	public void testListEmployees() {

		/*
		 * /oauth/token?username=myuser&password=mypassword
		 *	&client_id=mysupplycompany&client_secret=mycompanykey&grant_type=password
		 */
		ValidatableResponse loginResponse =
		given().
			//keystore("C:\\dev_apps\\wildfly-8.2.1.Final\\standalone\\configuration\\testkeystore.jks", "secret").
			//keystore("D:\\apache-tomcat-8.0.30\\conf\\tomcat.jks", "tomcat").
			formParam("username", "test").
			formParam("password", "test").
			formParam("client_id", "test01-id").
			formParam("client_secret", "test01-secretkey").
			formParam("grant_type", "password").
			formParam("scope", "read").
			baseUri("http://localhost").
			port(8080).
			/*header("Accept","application/vnd.eops.eserve+json").*/
			log().all(). //logs the request
		/*.expect().statusCode(200)*/
		/*.auth().basic("test", "test").*/

		when().
			/*.log().body().*/
			//post("https://localhost:8443/test01/oauth/token").
			post("/hr-service/oauth/token").
		then().
			log().all(); //logs the response


		String accessToken = loginResponse.extract().body().jsonPath().get("access_token");

		System.out.println("access_token: " + accessToken);

		ValidatableResponse listResponse =

		given().
			keystore("D:\\apache-tomcat-8.0.30\\conf\\tomcat.jks", "tomcat").
			formParam("status", "ACTIVE").
			header("Authorization", "Bearer " + accessToken.trim()).
			header("Accept","application/vnd.shipserv.hr+json").
			baseUri("https://localhost"). //for SSL request
			port(8443). //for SSL request
			log().all().
		when().
			get("/hr-service/protected/employee/list").

		then().
			log().all();

		System.out.println(listResponse.extract().asString());

		//List<Employee> employees = listResponse.extract().body().jsonPath().getList(".employees", Employee.class);
		//Map employees = response.extract().body().jsonPath().getList(".", Employee.class);
		//List<String> employeeNames = response.extract().body().jsonPath().getList(".", String.class);
		List<Employee> employees = Arrays.asList(listResponse.extract().body().as(Employee[].class));

		//System.out.println(employees.size());
		//Arrays.asList(response.getBody().as(Artwork[].class));

		for(Employee e: employees) {
			System.out.println("Employee Name: " + e.getName());
		}


	}

	@Test
	public void testAddEmployee() {

		/*
		 * /oauth/token?username=myuser&password=mypassword
		 *	&client_id=mysupplycompany&client_secret=mycompanykey&grant_type=password
		 */
		ValidatableResponse response =
		given().
			//keystore("C:\\dev_apps\\wildfly-8.2.1.Final\\standalone\\configuration\\testkeystore.jks", "secret").
			//keystore("D:\\apache-tomcat-8.0.30\\conf\\tomcat.jks", "tomcat").
			formParam("username", "test").
			formParam("password", "test").
			formParam("client_id", "test01-id").
			formParam("client_secret", "test01-secretkey").
			formParam("grant_type", "password").
			formParam("scope", "read").
			baseUri("http://localhost").
			port(8080).
			/*header("Accept","application/vnd.eops.eserve+json").*/
			log().all(). //logs the request
		/*.expect().statusCode(200)*/
		/*.auth().basic("test", "test").*/

		when().
			/*.log().body().*/
			//post("https://localhost:8443/test01/oauth/token").
			post("/hr-service/oauth/token").
		then().
			log().all(); //logs the response


		String accessToken = response.extract().body().jsonPath().get("access_token");

		System.out.println("access_token: " + accessToken);

		given().
			keystore("D:\\apache-tomcat-8.0.30\\conf\\tomcat.jks", "tomcat").
			formParam("name", "arya").
			formParam("address", "Westeros").
			formParam("contactNumber", "crow2").
			header("Authorization", "Bearer " + accessToken.trim()).
			header("Accept","application/vnd.shipserv.hr+json").
			baseUri("https://localhost"). //for SSL request
			port(8443). //for SSL request
			log().all().
		when().
			//get("http://localhost:8080/test01/protected/messaging/{name}").
			post("/hr-service/protected/employee/add").

		then().
			log().all();
	}

	@Test
	public void testUpdateEmployee() {

		/*
		 * /oauth/token?username=myuser&password=mypassword
		 *	&client_id=mysupplycompany&client_secret=mycompanykey&grant_type=password
		 */
		ValidatableResponse response =
		given().
			//keystore("C:\\dev_apps\\wildfly-8.2.1.Final\\standalone\\configuration\\testkeystore.jks", "secret").
			//keystore("D:\\apache-tomcat-8.0.30\\conf\\tomcat.jks", "tomcat").
			formParam("username", "test").
			formParam("password", "test").
			formParam("client_id", "test01-id").
			formParam("client_secret", "test01-secretkey").
			formParam("grant_type", "password").
			formParam("scope", "read").
			baseUri("http://localhost").
			port(8080).
			log().all(). //logs the request

		when().
			post("/hr-service/oauth/token").
		then().
			log().all(); //logs the response


		String accessToken = response.extract().body().jsonPath().get("access_token");

		System.out.println("access_token: " + accessToken);

		given().
			keystore("D:\\apache-tomcat-8.0.30\\conf\\tomcat.jks", "tomcat").
			formParam("id", "2").
			formParam("name", "arya").
			formParam("address", "Kings Landing").
			formParam("contactNumber", "crow2").
			formParam("status", "ACTIVE").
			header("Authorization", "Bearer " + accessToken.trim()).
			header("Accept","application/vnd.shipserv.hr+json").
			baseUri("https://localhost"). //for SSL request
			port(8443). //for SSL request
			log().all().
		when().
			post("/hr-service/protected/employee/update").
		then().
			log().all();
	}

	@Ignore
	public void testOauthTokenSSL() {

		/*
		 * /oauth/token?username=myuser&password=mypassword
		 *	&client_id=mysupplycompany&client_secret=mycompanykey&grant_type=password
		 */
		ValidatableResponse response =
		given().
			//keystore("C:\\dev_apps\\wildfly-8.2.1.Final\\standalone\\configuration\\testkeystore.jks", "secret").
			keystore("/Applications/apache-tomcat-8.0.23/conf/tomcat.jks", "tomcat").
			formParam("username", "test").
			formParam("password", "test").
			formParam("client_id", "test01-id").
			formParam("client_secret", "test01-secretkey").
			formParam("grant_type", "password").
			formParam("scope", "read").
			baseUri("https://localhost"). //for SSL request
			port(8443). //for SSL request
			/*header("Accept","application/vnd.eops.eserve+json").*/
			log().all(). //logs the request
		/*.expect().statusCode(200)*/
		/*.auth().basic("test", "test").*/

		when().
			/*.log().body().*/
			//post("https://localhost:8443/test01/oauth/token").
			post("/rest-oauth2/oauth/token").
		then().
			log().all(); //logs the response


		String accessToken = response.extract().body().jsonPath().get("access_token");

		//System.out.println("access_token: " + accessToken);

		given().
			//keystore("C:\\dev_apps\\wildfly-8.2.1.Final\\standalone\\configuration\\testkeystore.jks", "secret").
			keystore("/Applications/apache-tomcat-8.0.23/conf/tomcat.jks", "tomcat").
			pathParam("name", "arya").
			header("Authorization", "Bearer " + accessToken.trim()).
			header("Accept","application/vnd.eops.eserve+json").
			baseUri("https://localhost").
			port(8443).
			log().all().
		when().
			//get("https://localhost:8443/test01/protected/messaging/{name}").
			get("/rest-oauth2/protected/messaging/{name}").

		then().
			log().all();
	}

}
