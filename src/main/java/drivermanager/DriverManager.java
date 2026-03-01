package drivermanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

public class DriverManager {
    //threadLocal object for driver
    public static ThreadLocal<WebDriver> mydriver = new ThreadLocal<>();
    InputStream config = getClass().getClassLoader().getResourceAsStream("config.properties");
    static Properties props = new Properties();


    public WebDriver launchBrowser(String browser) throws IOException {
        URL gridURL = URI.create("http://localhost:4444/wd/hub").toURL();
        props.load(config);
        if (browser.equalsIgnoreCase("Chrome")) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setCapability("browserName", "chrome");
            if (props.getProperty("headless").equalsIgnoreCase("true")) {
                chromeOptions.addArguments("--headless");
            }
            if (props.getProperty("execution").equalsIgnoreCase("Grid")) {
                chromeOptions.addArguments("--headless");
                mydriver.set(new RemoteWebDriver(gridURL, chromeOptions));
            } else if (props.getProperty("execution").equalsIgnoreCase("Local")) {
                mydriver.set(new ChromeDriver(chromeOptions));
            }
        } else if (browser.equalsIgnoreCase("Firefox")) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setCapability("browserName", "firefox");

            if (props.getProperty("execution").equalsIgnoreCase("Grid")) {
                mydriver.set(new RemoteWebDriver(gridURL, firefoxOptions));
            } else if (props.getProperty("execution").equalsIgnoreCase("Local")) {
                mydriver.set(new ChromeDriver());
            }
        } else if (browser.equalsIgnoreCase("Edge")) {
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.setCapability("browserName", "MicrosoftEdge");

            if (props.getProperty("execution").equalsIgnoreCase("Grid")) {
                mydriver.set(new RemoteWebDriver(gridURL, edgeOptions));
            } else if (props.getProperty("execution").equalsIgnoreCase("Local")) {
                mydriver.set(new ChromeDriver());
            }
        }

        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        return getDriver();
    }

    public static synchronized WebDriver getDriver() {
        return mydriver.get();
    }

}