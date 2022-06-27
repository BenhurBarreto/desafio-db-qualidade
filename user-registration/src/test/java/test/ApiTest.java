package test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.junit.Test;

import dev.failsafe.internal.util.Assert;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ResponseBodyExtractionOptions;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
//import static io.restassured.matcher.RestAssuredMatchers.*;
//import static org.hamcrest.Matchers.*;

public class ApiTest {
	
	public class testeAPI1 {
		
//		Função que retorna o ID de cadastro do contato
		public int idNumber (String lastName) {
			RestAssured.baseURI = "https://api-de-tarefas.herokuapp.com";
			Response response = null;
			
			try {
				response = RestAssured.given()
		                .when()
		                .get("/contacts");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			JsonPath jsonPathValidator = response.jsonPath();
			
			List < String > allContactsNames = jsonPathValidator.getList("data.attributes.last_name");
			List < String > allContactsID = jsonPathValidator.getList("data.id");
			
			int contactIndex = allContactsNames.indexOf(lastName);
			int contactIndexID = allContactsID.indexOf(String.valueOf(contactIndex));
			
			System.out.println(allContactsNames.get(contactIndex));
	        System.out.println(allContactsID.get(contactIndex));
	        
	        return contactIndexID;
		}
		
//		Função que retorna o ID de cadastro do contato
		public int jsonIdIndex (String lastName) {
			RestAssured.baseURI = "https://api-de-tarefas.herokuapp.com";
			Response response = null;
			
			try {
				response = RestAssured.given()
		                .when()
		                .get("/contacts");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			JsonPath jsonPathValidator = response.jsonPath();
			
			List < String > allContactsNames = jsonPathValidator.getList("data.attributes.last_name");
			
			int contactIndex = allContactsNames.indexOf(lastName);
			
			System.out.println(allContactsNames.get(contactIndex));
	        
	        return contactIndex;
		}
	
		@Test
		public void IserirContato() {
			
			JSONObject request = new JSONObject();
			request.put("name", "Alex");
			request.put("last_name", "Lifeson");
			request.put("email", "alex@rush.com");
			request.put("age", "65");
			request.put("phone", "21122134");
			request.put("address", "Broadviwe Av.");
			request.put("state", "Otario");
			request.put("city", "Toronto");
			
			System.out.println(request);
			
			given()
				.header("Content-Type", "application/json")
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(request.toJSONString())
			.when()
				.post("https://api-de-tarefas.herokuapp.com/contacts")
			.then()
				.statusCode(201);
//				.log().all();
		}
		
		@Test
		public void ListarContatos() {
			
			int jsonInd = jsonIdIndex("Lifeson");
			int ID = idNumber("Lifeson");
			
			given()
				.get("https://api-de-tarefas.herokuapp.com/contacts")
			.then()
				.statusCode(200)
				.body("data.id[" + String.valueOf(jsonInd) + "]", equals(ID));
	    
		}
		
		@Test
		public void EditarContato() {
					
			JSONObject request = new JSONObject();
			request.put("name", "Alex");
			request.put("last_name", "Zivojinovic Lifeson");
			request.put("email", "alex@rush.com");
			request.put("age", "65");
			request.put("phone", "21122134");
			request.put("address", "Broadviwe Av.");
			request.put("state", "Otario");
			request.put("city", "Toronto");
			
			System.out.println(request);
			
			int ID = idNumber("Lifeson");
			
			given()
				.header("Content-Type", "application/json")
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(request.toJSONString())
			.when()
				.put("https://api-de-tarefas.herokuapp.com/contacts/" + String.valueOf(ID))
			.then()
				.statusCode(200);
//				.log().all();
		}
		
		@Test
		public void ExcluirContato() {
			
			int ID = idNumber("Lifeson");
			
			when()
				.delete("https://api-de-tarefas.herokuapp.com/contacts/" + String.valueOf(ID))
			.then()
				.statusCode(204);
//				.log().all();
		}
	}
}
