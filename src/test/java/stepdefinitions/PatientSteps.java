package stepdefinitions;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;

import apiEngine.model.request.PostPatientRequest;
import apiEngine.model.request.PutPatientRequest;
import apiEngine.model.response.PostPatientResponse;
import dataProviders.ConfigReader;
import dataProviders.ExcelReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.opentelemetry.api.internal.StringUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import utilities.LoggerLoad;
import utilities.RestAssuredRequestFilter;

public class PatientSteps extends BaseStep {

	static final String baseUrl = ConfigReader.getProperty("base.url");
	static final String batchUrl = ConfigReader.getProperty("patient.url");
	RequestSpecification request;
	Response response;
	PostPatientRequest postPatientRequest;
	PutPatientRequest putPatientRequest;

	Map<String, String> excelDataMap;

	PostPatientResponse postPatientResponse;
	static String patientId;

	public void SetupPreRequisites() {
		try {
			excelDataMap = null;
			// create patient
			excelDataMap = ExcelReader.getData("post_patient_valid", "patient");
			if (null != excelDataMap && excelDataMap.size() > 0) {
				postPatientRequest = new PostPatientRequest(excelDataMap.get("firstname"), excelDataMap.get("lastname"),
						Long.parseLong(excelDataMap.get("contactnumber")), excelDataMap.get("email"),
						excelDataMap.get("allergy"), excelDataMap.get("foodcat"), excelDataMap.get("dob"));
			}

			response = postPatientEndpoints.postPatient(postPatientRequest);
			String jsonAsString = response.asString();
			Integer patientId = response.path("patientId");

			System.out.println("post paitinet id" + patientId);
			if(null!=patientId) {
				ConfigReader.setProperty("patient.patientId", patientId.toString());
				LoggerLoad.logDebug("patientId - " + patientId);
			}
			
		} catch (Exception ex) {
			LoggerLoad.logError(ex.getMessage());
			ex.printStackTrace();
		}

	}
	
	public void setupPatientToken() {
		
	}

	/*
	 * public void Cleanup() { // delete program RestAssured.baseURI = baseUrl;
	 * request = RestAssured.given(); response =
	 * request.delete(ProgramRoutes.deleteProgramById(programId)); }
	 */

	@Given("User creates POST Request for the dietician\\/patient API endpoint with fields from {string} with {string}")
	public void user_creates_post_request_for_the_dietician_patient_api_endpoint_with_fields_from_with(String sheetName,
			String dataKey) {
		try {

			RestAssured.baseURI = baseUrl;
			RequestSpecification request = RestAssured.given().filter(new RestAssuredRequestFilter());
			request.header("Content-Type", "application/json");

			excelDataMap = null;
			String firstname = null;
			String lastname = null;
			String email = null;
			String allergy = null;
			String foodcat = null;
			String dob = null;
			long contactnumber = 0;

			excelDataMap = ExcelReader.getData(dataKey, sheetName);

			if (null != excelDataMap && excelDataMap.size() > 0) {

				if (!excelDataMap.get("firstname").isBlank()) {
					firstname = excelDataMap.get("firstname");
				}
				if (!excelDataMap.get("lastname").isBlank()) {
					lastname = excelDataMap.get("lastname");

				}
				if (!excelDataMap.get("contactnumber").isBlank()) {
					contactnumber = Long.parseLong(excelDataMap.get("contactnumber"));
				}
				if (!excelDataMap.get("email").isBlank()) {
					email = excelDataMap.get("email");
				}
				if (!excelDataMap.get("allergy").isBlank()) {
					allergy = excelDataMap.get("allergy");
				}
				if (!excelDataMap.get("foodcat").isBlank()) {
					foodcat = excelDataMap.get("foodcat");
				}
				if (!excelDataMap.get("dob").isBlank()) {
					dob = excelDataMap.get("dob");
				}

			}
			postPatientRequest = new PostPatientRequest(firstname, lastname, contactnumber, email, allergy, foodcat,
					dob);
			LoggerLoad.logInfo("Paitient POST request created");
			if(dataKey.contains("invalid_token")) {
				ConfigReader.setProperty("login.token", "invalidtoken!!!");
			}
			if(dataKey.contains("patient_token")) {
				setupPatientToken();
			}

		} catch (Exception e) {
			LoggerLoad.logInfo(e.getMessage());
			e.printStackTrace();
		}

	}

	@When("User sends HTTP POST patient Request")
	public void user_sends_http_post_patient_request() {
		try {
			response = postPatientEndpoints.postPatient(postPatientRequest);
			LoggerLoad.logInfo("Patient POST request sent");
		} catch (Exception ex) {
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}

	}

	@Then("User receives response for POST patient {string} with {string}")
	public void user_receives_response_for_post_patient_with(String sheetName, String dataKey) {
		try {
			response.then().log().all().extract().response();

			switch (dataKey) {
			case "post_patient_valid":
				response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_CREATED)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(
								getClass().getClassLoader().getResourceAsStream("postpatientresponseschema.json")));

				// PostPatientResponse postPatientResponse =
				// response.getBody().as(PostPatientResponse.class);
				String jsonAsString = response.asString();
				Integer patientId = response.path("patientId");

				System.out.println("post paitinet id" + patientId);

				ConfigReader.setProperty("patient.patientId", patientId.toString());
				LoggerLoad.logDebug("patientId - " + patientId);

				break;
				
			case "post_patient_duplicate":
				
				response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_BAD_REQUEST)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
						getClass().getClassLoader().getResourceAsStream("400responseschema.json")));
				break;
				
			case "post_patient_missing_mandatory_firstname":
			case "post_patient_missing_mandatory_lastname":
			case "post_patient_missing_mandatory_contactnumber":
			case "post_patient_missing_mandatory_email":
			case "post_patient_missing_mandatory_dob":
			case "post_patient_incorrect_format_allery":
			case "post_patient_incorrect_format_foodcat":
			case "post_patient_incorrect_format_dob":
			case "post_patient_incorrect_format_email":
			case "post_patient_incorrect_format_contactnumber":	
				
				response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_BAD_REQUEST)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
						getClass().getClassLoader().getResourceAsStream("400responseschema.json")));
				break;
				
			case "post_patient_invalid_endpoint":
				
				response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_NOT_FOUND)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
						getClass().getClassLoader().getResourceAsStream("401or403or404or500responseschema.json")));
				
				break;
			
			case "post_patient_invalid_token":	
				
				response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_UNAUTHORIZED)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
						getClass().getClassLoader().getResourceAsStream("401or403or404or500responseschema.json")));
				break;
				
			case "post_patient_patient_token":	
				
				response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_FORBIDDEN)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
						getClass().getClassLoader().getResourceAsStream("401or403or404or500responseschema.json")));
				break;
				
			

			default:
				assertTrue(false);
				break;
			}
		} catch (Exception ex) {
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}

	}

	@Given("User creates Put Request for the dietician\\/patient API endpoint with fields from {string} with {string}")
	public void user_creates_put_request_for_the_dietician_patient_api_endpoint_with_fields_from_with(String sheetName,
			String dataKey) {
		try {

			if (dataKey.equals("put_patient_valid")) {
				SetupPreRequisites();
			}

			RestAssured.baseURI = baseUrl;
			RequestSpecification request = RestAssured.given().filter(new RestAssuredRequestFilter());
			request.header("Content-Type", "application/json");

			excelDataMap = null;
			String firstname = null;
			String lastname = null;
			String email = null;
			String allergy = null;
			String foodcat = null;
			String dob = null;
			long contactnumber = 0;

			excelDataMap = ExcelReader.getData(dataKey, sheetName);

			if (null != excelDataMap && excelDataMap.size() > 0) {

				if (!excelDataMap.get("firstname").isBlank()) {
					firstname = excelDataMap.get("firstname");
				}
				if (!excelDataMap.get("lastname").isBlank()) {
					lastname = excelDataMap.get("lastname");

				}
				if (!excelDataMap.get("contactnumber").isBlank()) {
					contactnumber = Long.parseLong(excelDataMap.get("contactnumber"));
				}
				if (!excelDataMap.get("email").isBlank()) {
					email = excelDataMap.get("email");
				}
				if (!excelDataMap.get("allergy").isBlank()) {
					allergy = excelDataMap.get("allergy");
				}
				if (!excelDataMap.get("foodcat").isBlank()) {
					foodcat = excelDataMap.get("foodcat");
				}
				if (!excelDataMap.get("dob").isBlank()) {
					dob = excelDataMap.get("dob");
				}

			}
			postPatientRequest = new PostPatientRequest(firstname, lastname, contactnumber, email, allergy, foodcat,
					dob);
			System.out.println("put paitient " + postPatientRequest.toString());
			LoggerLoad.logInfo("PUT request created");
			if(dataKey.contains("invalid_token")) {
				ConfigReader.setProperty("login.token", "invalidtoken!!!");
			}
			if(dataKey.contains("patient_token")) {
				setupPatientToken();
			}

		} catch (Exception e) {
			LoggerLoad.logInfo(e.getMessage());
			e.printStackTrace();
		}

	}

	@When("User sends HTTP Put patient Request {string}")
	public void user_sends_http_put_patient_request(String dataKey) {

		try {
			response = postPatientEndpoints.putPatient(postPatientRequest, dataKey);
			LoggerLoad.logInfo("Patient POST request sent");
		} catch (Exception ex) {
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}

	}

	@Then("User receives response for Put patient {string} with {string}")
	public void user_receives_response_for_put_patient_with(String sheetName, String dataKey) {
		try {
			response.then().log().all().extract().response();

			switch (dataKey) {
			case "put_patient_valid":
				response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_OK)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(
								getClass().getClassLoader().getResourceAsStream("postpatientresponseschema.json")));

				// PostPatientResponse postPatientResponse =
				// response.getBody().as(PostPatientResponse.class);
				String jsonAsString = response.asString();
				Integer patientId = response.path("patientId");

				System.out.println("post paitinet id" + patientId);

				ConfigReader.setProperty("patient.patientId", patientId.toString());
				LoggerLoad.logDebug("patientId - " + patientId);

				break;
				
			case "put_patient_duplicate":
				
				response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_BAD_REQUEST)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
						getClass().getClassLoader().getResourceAsStream("400responseschema.json")));
				break;
				
			
			case "put_patient_incorrect_format_allery":
			case "put_patient_incorrect_format_foodcat":
			case "put_patient_incorrect_format_dob":
			case "put_patient_incorrect_format_email":
			case "put_patient_incorrect_format_contactnumber":	
				
				response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_BAD_REQUEST)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
						getClass().getClassLoader().getResourceAsStream("400responseschema.json")));
				break;
				
			case "put_patient_invalid_endpoint":
				
				response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_NOT_FOUND)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
						getClass().getClassLoader().getResourceAsStream("401or403or404or500responseschema.json")));
				
				break;
			
			case "put_patient_invalid_token":	
				
				response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_UNAUTHORIZED)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
						getClass().getClassLoader().getResourceAsStream("401or403or404or500responseschema.json")));
				break;
				
			case "put_patient_patient_token":	
				
				response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_FORBIDDEN)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
						getClass().getClassLoader().getResourceAsStream("401or403or404or500responseschema.json")));
				break;
				
			

			default:
				assertTrue(false);
				break;
			}
		} catch (Exception ex) {
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}

	}
	
	@Given("User creates GET Request for the dietician\\/patient API endpoint with fields from {string} with {string}")
	public void user_creates_get_request_for_the_dietician_patient_api_endpoint_with_fields_from_with(String sheetName, String dataKey) {
		try {

			if (dataKey.equals("get_patient_valid")) {
				SetupPreRequisites();
			}

			RestAssured.baseURI = baseUrl;
			RequestSpecification request = RestAssured.given().filter(new RestAssuredRequestFilter());
			request.header("Content-Type", "application/json");
			LoggerLoad.logInfo("Get request created");
			
			if(dataKey.contains("invalid_token")) {
				ConfigReader.setProperty("login.token", "invalidtoken!!!");
			}
			if(dataKey.contains("patient_token")) {
				setupPatientToken();
			}

		} catch (Exception e) {
			LoggerLoad.logInfo(e.getMessage());
			e.printStackTrace();
		}

	}

	@When("User sends HTTP Get patient Request {string}")
	public void user_sends_http_get_patient_request(String dataKey) {
		try {
			response = postPatientEndpoints.getPatient(dataKey);
			LoggerLoad.logInfo("Patient POST request sent");
		} catch (Exception ex) {
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}

	}

	@Then("User receives response for Get patient {string} with {string}")
	public void user_receives_response_for_get_patient_with(String sheetName, String dataKey) {
		try {
			response.then().log().all().extract().response();

			switch (dataKey) {
			case "get_patient_valid":
				response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_OK);
						
						/*// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(
								getClass().getClassLoader().getResourceAsStream("getpatientresponseschema.json")));*/

				

				break;
				
				
			case "get_patient_invalid_endpoint":
				
				response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_NOT_FOUND)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
						getClass().getClassLoader().getResourceAsStream("401or403or404or500responseschema.json")));
				
				break;
			
			case "get_patient_invalid_token":	
				
				response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_UNAUTHORIZED)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
						getClass().getClassLoader().getResourceAsStream("401or403or404or500responseschema.json")));
				break;
				
			case "get_patient_patient_token":	
				
				response.then().assertThat()
				// Validate response status
				.statusCode(HttpStatus.SC_FORBIDDEN)
				// Validate content type
				.contentType(ContentType.JSON)
				// Validate json schema
				.body(JsonSchemaValidator.matchesJsonSchema(
						getClass().getClassLoader().getResourceAsStream("401or403or404or500responseschema.json")));
				break;
				
			

			default:
				assertTrue(false);
				break;
			}
		} catch (Exception ex) {
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}

	}


	
}
