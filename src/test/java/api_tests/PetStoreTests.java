package api_tests;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import java.io.File;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utilities.BrowserUtils;

public class PetStoreTests {

	BrowserUtils utils = new BrowserUtils();
	Response response;
	int petId;
	
	@BeforeTest
	public void setup() {
		baseURI = "https://petstore.swagger.io/v2";
	}
	
	
//	@AfterMethod
//	public void cleanup() {
//		deleteThePet();
//	}
	
	@Test
	public void createApet() {
		petId = 111 + utils.randomNumber();
		
		String endpoint = "/pet";
		String requestBody = "{\n"
				+ "  \"id\": "+petId+",\n"
				+ "  \"category\": {\n"
				+ "    \"id\": 21,\n"
				+ "    \"name\": \"Dog\"\n"
				+ "  },\n"
				+ "  \"name\": \"Mountain\",\n"
				+ "  \"photoUrls\": [\n"
				+ "    \"string\"\n"
				+ "  ],\n"
				+ "  \"tags\": [\n"
				+ "    {\n"
				+ "      \"id\": 21999,\n"
				+ "      \"name\": \"M_21999\"\n"
				+ "    }\n"
				+ "  ],\n"
				+ "  \"status\": \"pending\"\n"
				+ "}";
		
		response = given()
		.contentType("application/json")
		.accept(ContentType.JSON)
		.body(requestBody)
		.when()
		.post(endpoint)
		.thenReturn();
		
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getContentType(), "application/json");
		Assert.assertEquals(response.jsonPath().getInt("id"), petId);
		
		System.out.println(response.path("name").toString());
		System.out.println(response.path("category.name").toString());
		System.out.println(response.jsonPath().get("id").toString());
		System.out.println(response.jsonPath().get("status").toString());
		System.out.println(response.jsonPath().get("tags[0].name").toString());
		
	}
	
	@Test(dependsOnMethods = "createApet")
	public void updatePet() {
		
		String endpoint = "/pet";
		String requestBody = "{\n"
				+ "  \"id\": "+petId+",\n"
				+ "  \"category\": {\n"
				+ "    \"id\": 21,\n"
				+ "    \"name\": \"Dog\"\n"
				+ "  },\n"
				+ "  \"name\": \"Shadow\",\n"
				+ "  \"photoUrls\": [\n"
				+ "    \"string\"\n"
				+ "  ],\n"
				+ "  \"tags\": [\n"
				+ "    {\n"
				+ "      \"id\": 21999,\n"
				+ "      \"name\": \"M_21999\"\n"
				+ "    }\n"
				+ "  ],\n"
				+ "  \"status\": \"available\"\n"
				+ "}";
		
		response = given()
		.contentType("application/json")
		.accept(ContentType.JSON)
		.body(requestBody)
		.when()
		.put(endpoint)
		.thenReturn();
		
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getContentType(), "application/json");
		Assert.assertEquals(response.jsonPath().getInt("id"), petId);
		Assert.assertEquals(response.jsonPath().getString("name"), "Shadow");
		
		System.out.println(response.path("name").toString());
		System.out.println(response.path("category.name").toString());
		System.out.println(response.jsonPath().get("id").toString());
		System.out.println(response.jsonPath().get("status").toString());
		System.out.println(response.jsonPath().get("tags[0].name").toString());
	}
	
	
	@Test (dependsOnMethods = "createApet")
	public void findAPetById() {
		String endpoint = "/pet/" + petId;
		
		response = given()
		.contentType("application/json")
		.accept(ContentType.JSON)
		.get(endpoint)
		.thenReturn();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getContentType(), "application/json");
		Assert.assertEquals(response.jsonPath().getInt("id"), petId);
		Assert.assertEquals(response.jsonPath().getInt("category.id"), 21);
		Assert.assertEquals(response.jsonPath().getString("category.name"), "Dog");
		Assert.assertEquals(response.jsonPath().getString("name"), "Mountain");
		Assert.assertEquals(response.jsonPath().getString("status"), "pending");
	}
	
	
	@Test (dependsOnMethods = "createApet")
	public void findAPetById_chainValidation() {
		String endpoint = "/pet/" + petId;
		
		response = given()
		.contentType("application/json")
		.accept(ContentType.JSON)
		.get(endpoint).thenReturn();
		
		response.prettyPrint();
		
		response
		.then().assertThat().statusCode(200)
		.and().assertThat().contentType("application/json")
		.and().assertThat().body("id", Matchers.equalTo(petId))
		.and().assertThat().body("category.id", Matchers.equalTo(21))
		.and().assertThat().body("category.name", Matchers.equalTo("Dog"))
		.and().assertThat().body("name", Matchers.equalTo("Mountain"))
		.and().assertThat().body("tags[0].name", Matchers.equalTo("M_21999"))
		.and().assertThat().body("status", Matchers.equalTo("pending"));
	}
	
	@Test (dependsOnMethods = "createApet")
	public void deleteThePet() {
		String endpoint = "/pet/" + petId;
		
		response = given().accept(ContentType.JSON)
		.contentType("application/json")
		.delete(endpoint).thenReturn();
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getContentType(), "application/json");
		Assert.assertEquals(response.jsonPath().getString("message"), petId + "");
	}
	
	@Test
	public void createApet_with_json_file() {
		
		String endpoint = "/pet";
		File requestBody = new File("./src/test/resources/json_files/createApet.json");
		
		response = given()
		.contentType("application/json")
		.accept(ContentType.JSON)
		.body(requestBody)
		.when()
		.post(endpoint)
		.thenReturn();
		
		petId = response.jsonPath().getInt("id");
		
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getContentType(), "application/json");
		Assert.assertEquals(response.jsonPath().getInt("id"), petId);
		
		System.out.println(response.path("name").toString());
		System.out.println(response.path("category.name").toString());
		System.out.println(response.jsonPath().get("id").toString());
		System.out.println(response.jsonPath().get("status").toString());
		System.out.println(response.jsonPath().get("tags[0].name").toString());
	}
	
	@Test
	public void petNotFound() {
		String endpoint = "/pet/" + 648730;
		response = given().accept(ContentType.JSON).contentType("application/json").get(endpoint).thenReturn();
		
		Assert.assertEquals(response.getStatusCode(), 404);
		Assert.assertEquals(response.getContentType(), "application/json");
		
		response.prettyPrint();
		Assert.assertEquals(response.jsonPath().getString("message"), "Pet not found");
	}
	
	@Test
	public void findPetsByStatus() {
		// The test is directly calling to the end point and validating just two items 
		// status code and content type
		
		
		String endpoint = "/pet/findByStatus";
		
		given().contentType("application/json")
		.accept(ContentType.JSON)
		.when().get(endpoint + "?status=sold")
		.then().statusCode(200)
		.and().contentType("application/json");
	}
	
	@Test
	public void findPetsByStatus_providingQueryParam() {
		// The test is directly calling to the end point with query parameter as a header 
		// and getting the response into a Response object, 
		// Then validating the status code and content type
		String endpoint = "/pet/findByStatus";
		
		response = given().contentType("application/json")
		.accept(ContentType.JSON)
		.queryParam("status", "sold")
		.when().get(endpoint)
		.thenReturn();
		
		response.prettyPrint();
		
		assertEquals(response.getStatusCode(), 200);
		assertEquals(response.getContentType(), "application/json");
	}
	
}
