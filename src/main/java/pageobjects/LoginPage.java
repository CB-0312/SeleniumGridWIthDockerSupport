package pageobjects;
 import org.openqa.selenium.By;
 import org.openqa.selenium.WebDriver;
 import org.openqa.selenium.support.ui.ExpectedConditions;
 import org.openqa.selenium.support.ui.WebDriverWait;
public class LoginPage {
    WebDriver driver;
      // Locators
     By usernameField = By.cssSelector("input[name='username']");
     By passwordField = By.cssSelector("input[type='password']");
     By loginButton = By.cssSelector("button[type='submit']");
    public LoginPage(WebDriver driver) {
         this.driver = driver;
     }
    public void navigateToLoginPage(){
            driver.get("https://opensource-demo.orangehrmlive.com/");
    }
     public void enterUsername(String username) throws InterruptedException {
         Thread.sleep(5000);
         driver.findElement(usernameField).sendKeys(username);
     }
     public void enterPassword(String password) {
         driver.findElement(passwordField).sendKeys(password);
     }

     public void clickLogin() {
         driver.findElement(loginButton).click();
     }
    public void verifyErrorMessage(){
         WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='alert alert-danger']")));
    }
    public void logout(){
         driver.findElement(By.cssSelector("a[id='welcome']")).click();
         driver.findElement(By.cssSelector("a[href='/web/index.php/auth/logout']")).click();
    }
 }