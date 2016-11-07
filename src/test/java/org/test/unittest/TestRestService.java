package org.test.unittest;

import static com.jayway.restassured.RestAssured.given;

import org.junit.Test;

import com.jayway.restassured.response.ValidatableResponse;

public class TestRestService {
	
	@Test
	public void testOauthToken() {
		
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
			pathParam("name", "arya").
			header("Authorization", "Bearer " + accessToken.trim()).
			header("Accept","application/vnd.eops.eserve+json").
			log().all().
		when().
			//get("http://localhost:8080/test01/protected/messaging/{name}").
			get("/rest-oauth2/messaging/{name}").
		
		then().
			log().all();
	}
	
	@Test
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
