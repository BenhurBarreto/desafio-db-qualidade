package test;

import java.util.List;

import org.json.simple.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApiTest {
	
	public static String jsonID (String lastName) {
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
		
		List < String > allContactsNames = jsonPathValidator.getList("data.attributes.last-name");
		List < String > allContactsID = jsonPathValidator.getList("data.id");
		
		int contactIndex = allContactsNames.indexOf(lastName);
		int contactIndexID = allContactsID.indexOf(String.valueOf(contactIndex));
		
		System.out.println(allContactsNames.get(contactIndex));
        System.out.println(allContactsID.get(contactIndex));
        
        String ID = allContactsID.get(contactIndex);
        
        return ID;
	}
	
	public static void wait(int ms)	{
	    try {
	        Thread.sleep(ms);
	    }
	    catch(InterruptedException ex) {
	        Thread.currentThread().interrupt();
	    }
	}
	
	@Test
	public void A_IserirContato() {
		System.out.println("Teste Inserir Contato");
				
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
		System.out.println();
		
		given()
			.header("Content-Type", "application/json")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(request.toJSONString())
		.when()
			.post("https://api-de-tarefas.herokuapp.com/contacts")
		.then()
			.statusCode(201);
		
		System.out.println();
	}
	
	@Test
	public void B_ListarContato() {
		wait(1000);
		System.out.println("Teste ListarContato");
		
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
 
        List < String > allContactsNames = jsonPathValidator.getList("data.attributes.last-name");
        List < String > allContactsID = jsonPathValidator.getList("data.id");

        int contactIndex = allContactsNames.indexOf("Lifeson");
        
        System.out.println("id: " + allContactsID.get(contactIndex));
        System.out.println("name " + jsonPathValidator.getList("data.attributes.name").get(contactIndex));
        System.out.println("last-name " + jsonPathValidator.getList("data.attributes.last-name").get(contactIndex));
        System.out.println("email " + jsonPathValidator.getList("data.attributes.email").get(contactIndex));
        System.out.println("age " + jsonPathValidator.getList("data.attributes.age").get(contactIndex));
        System.out.println("phone " + jsonPathValidator.getList("data.attributes.phone").get(contactIndex));
        System.out.println("address " + jsonPathValidator.getList("data.attributes.address").get(contactIndex));
        System.out.println("state " + jsonPathValidator.getList("data.attributes.state").get(contactIndex));
        System.out.println("city " + jsonPathValidator.getList("data.attributes.city").get(contactIndex));
        System.out.println();
    }
	
	@Test
	public void C_EditarContato() {
		wait(1000);
		System.out.println("Teste Editar Contato");
		
		JSONObject request = new JSONObject();
		request.put("name", "Alex");
		request.put("last_name", "Zivojinovic Lifeson");
		request.put("email", "alex@rush.com");
		request.put("age", "65");
		request.put("phone", "21122134");
		request.put("address", "Broadviwe Av.");
		request.put("state", "Otario");
		request.put("city", "Toronto");
		
		String ID = jsonID("Lifeson");
		System.out.println("https://api-de-tarefas.herokuapp.com/contacts/" + ID);
		System.out.println();
		
		given()
			.header("Content-Type", "application/json")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(request.toJSONString())
		.when()
			.put("https://api-de-tarefas.herokuapp.com/contacts/" + ID)
		.then()
			.statusCode(200);	
	}
	
	@Test
	public void D_ExcluirContato() {
		wait(1000);
		System.out.println("Teste Excluir Contato");
		
		String ID = jsonID("Zivojinovic Lifeson");
		System.out.println("https://api-de-tarefas.herokuapp.com/contacts/" + ID);
		
		when()
			.delete("https://api-de-tarefas.herokuapp.com/contacts/" + ID)
		.then()
			.statusCode(204);
		System.out.println();
		wait(1000);
	}
}
