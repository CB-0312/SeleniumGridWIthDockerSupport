package stepdefinitions;

import drivermanager.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pageobjects.LoginPage;

public class LoginSteps {
   public static WebDriver driver = null;


    private LoginPage loginPage = new LoginPage(DriverManager.getDriver());

    @Given("the user is on the login page")
    public void the_user_is_on_the_login_page() {
         loginPage.navigateToLoginPage();
    }

    @When("the user enters valid credentials")
    public void the_user_enters_valid_credentials() throws InterruptedException {
        loginPage.enterUsername("validUser");
        loginPage.enterPassword("validPassword");
        loginPage.clickLogin();
    }

    @Then("the user should be redirected to the home page")
    public void the_user_should_be_redirected_to_the_home_page() {

     }

    @When("the user enters invalid credentials")
    public void theUserEntersInvalidCredentials() throws InterruptedException {

        loginPage.enterUsername("validUser");
        loginPage.enterPassword("validPassword123");
        loginPage.clickLogin();
    }

    @Then("the user should see an error message")
    public void theUserShouldSeeAnErrorMessage() {
        loginPage.verifyErrorMessage();
        loginPage.logout();
    }


}