package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.MainPage;

import java.time.Duration;

public class TestBase {
    protected WebDriver driver;
    protected MainPage mainPage;

    public void startBrowserFirefox() {
        WebDriverManager.firefoxdriver().setup(); // СНАЧАЛА setup
        driver = new FirefoxDriver();
        mainPage = new MainPage(driver);
    }

    public void startBrowserChrome() {
        WebDriverManager.chromedriver().setup(); // СНАЧАЛА setup
        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
    }

    @Before
    public void initBrowser() {
        String browser = System.getProperty("browser", "chrome"); // chrome — по умолчанию
        if (browser.equalsIgnoreCase("firefox")) {
            startBrowserFirefox();
        } else {
            startBrowserChrome();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");
        acceptCookies();
    }

    private void acceptCookies() {
        try {
            By cookieButton = By.xpath("//button[contains(text(), 'да все привыкли')]");
            driver.findElement(cookieButton).click();
        } catch (Exception e) {
            System.out.println("Cookie banner not found or already accepted");
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
