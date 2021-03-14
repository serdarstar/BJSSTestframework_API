package com.bookit.step_definitions;

import com.bookit.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;


import static io.restassured.RestAssured.*;
public class ApiStepDefs {



    Response get;
    Response get1;
    Response post;
    Response put;
    Response delete;


    @When("I get the current user information from api")
    public void i_get_the_current_user_information_from_api() {

        String url = ConfigurationReader.get("url")+"users";
        get = given().accept(ContentType.JSON).when().get(url);
    }

    @Then("status code should be {int}")
    public void status_code_should_be(int statusCode) {

        Assert.assertEquals(get.statusCode(),statusCode);
    }


    @Then("the response should have a first name {string}")
    public void theResponseShouldHaveAFirstName(String firstName) {

        Assert.assertTrue(get.body().asString().contains(firstName));
    }

    @Then("the response headers should have {string} header")
    public void theResponseHeadersShouldHaveHeader(String header) {

        Assert.assertTrue(get.headers().hasHeaderWithName(header));
    }

    @When("I get user information from api for user {int}")
    public void iGetUserInformationFromApiForUser(int id) {
        String url = ConfigurationReader.get("url")+"users/"+id;
        get1 = given().accept(ContentType.JSON).when().get(url);

        Assert.assertEquals(get1.statusCode(),200);

    }

    @Then("the user info should match the following values")
    public void theUserInfoShouldMatchTheFollowingValues() {

        JsonPath jsonPath = get1.jsonPath();
        int idJson = jsonPath.getInt("data.id");
        String email = jsonPath.getString("data.email");
        String first_name = jsonPath.getString("data.first_name");
        String last_name = jsonPath.getString("data.last_name");

        Assert.assertEquals(idJson,2);
        Assert.assertEquals(first_name,"Janet");
        Assert.assertEquals(last_name,"Weaver");
        Assert.assertEquals(email,"janet.weaver@reqres.in");
    }

    @When("the user send a POST request")
    public void theUserSendAPOSTRequest() {
        String jsonBody = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";

         post = given().log().all()
                .accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(jsonBody)
                .when()
                .post(ConfigurationReader.get("url")+"users");

    }

    @Then("the status code should be {int}")
    public void theStatusCodeShouldBe(int statusCode) {

        Assert.assertEquals(post.statusCode(), statusCode);
    }

    @And("the user info should be correct")
    public void theUserInfoIsCorrect() {

        String name = post.path("name");
        String gender = post.path("job");

        Assert.assertEquals(name, "morpheus");
        Assert. assertEquals(gender, "leader");

    }

    @Given("the user sends PUT request")
    public void theUserSendsPUTRequest() {

        String jsonBody = "{\n" +
                "    \"name\": \"neo\",\n" +
                "    \"job\": \"the one\"\n" +
                "}";

        put = given().log().all()
                .accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(jsonBody)
                .when()
                .put(ConfigurationReader.get("url")+"users/3");
        put.prettyPrint();
    }

    @Then("the response code should be {int}")
    public void theResponseCodeShouldBe(int statusCode) {

        Assert.assertEquals(put.statusCode(), statusCode);
    }

    @And("the response should have updated info")
    public void theResponseShouldHaveUpdatedInfo() {

        JsonPath jsonPath = put.jsonPath();

        String name = jsonPath.getString("name");
        String job = jsonPath.getString("job");

        Assert.assertEquals(name,"neo");
        Assert.assertEquals(job,"the one");



    }

    @Given("the user sends a DELETE request")
    public void theUserSendsADELETERequest() {

        delete = given().log().all()
                .accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .when()
                .delete(ConfigurationReader.get("url")+"users/5");
    }

    @Then("the delete status code should be {int}")
    public void theDeleteStatusCodeShouldBe(int statusCode) {
        Assert.assertEquals(delete.statusCode(), statusCode);

    }
}
