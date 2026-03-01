package hooks;

import drivermanager.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.AfterAll;
import org.openqa.selenium.WebDriver;
import seleniumgridmanager.SeleniumGridManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MyHooks {
    DriverManager driverManager = null;
    private WebDriver driver = null;
    static Properties props= new Properties();
    static InputStream config;

    // manager used to start/stop Selenium Grid for the test suite
    private static final SeleniumGridManager gridManager = new SeleniumGridManager();


    @BeforeAll
    public static void beforeAll() {


        try {
            config = MyHooks.class.getClassLoader().getResourceAsStream("config.properties");
            if (config != null) {
                props.load(config);
                System.out.println("[MyHooks] Loaded config.properties successfully.");
            } else {
                System.err.println("[MyHooks] config.properties not found on classpath.");
            }
        } catch (IOException e) {
            System.err.println("[MyHooks] Error loading config.properties: " + e.getMessage());
            e.printStackTrace();
        }


        if (props.getProperty("execution").equalsIgnoreCase("Grid")) {
            System.out.println("[MyHooks] BeforeAll - starting Selenium Grid via SeleniumGridManager...");
            try {
                gridManager.startGrid();
                System.out.println("[MyHooks] Selenium Grid start triggered.");
            } catch (IOException e) {
                System.err.println("[MyHooks] Failed to start Selenium Grid: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @AfterAll
    public static void afterAll() {
        if (props.getProperty("execution").equalsIgnoreCase("Grid")) {
            System.out.println("[MyHooks] AfterAll - stopping Selenium Grid via SeleniumGridManager...");
            try {
                gridManager.stopGrid();
                System.out.println("[MyHooks] Selenium Grid stop triggered.");
            } catch (Exception e) {
                System.err.println("[MyHooks] Failed to stop Selenium Grid: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Before
    public void setup() throws IOException {
        System.out.println("This is setup method");
        driverManager = new DriverManager();

        // Read browser from config.properties. Default to "Edge" if not found.
        String browser = "Chrome"; // default browser
        try {
            if (config != null) {
                props.load(config);
                String configBrowser = props.getProperty("browser");
                if (configBrowser != null && !configBrowser.trim().isEmpty()) {
                    browser = configBrowser.trim();
                    System.out.println("[MyHooks] Browser from config.properties: " + browser);
                } else {
                    System.out.println("[MyHooks] 'browser' property not found in config.properties — using default: " + browser);
                }
            } else {
                System.out.println("[MyHooks] config.properties not found on classpath — using default: " + browser);
            }
        } catch (IOException e) {
            System.err.println("[MyHooks] Error reading config.properties: " + e.getMessage() + " — using default: " + browser);
        }

        driver = driverManager.launchBrowser(browser);
    }

    @After
    public void tearDown() {
        System.out.println("This is tearDown method");
        if (driver != null) {
            driver.quit();
        }
    }
}
