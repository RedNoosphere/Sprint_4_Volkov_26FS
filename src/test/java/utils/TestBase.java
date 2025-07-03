package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.MainPage;

import java.time.Duration;

import static pages.MainPage.BASE_URL;

public class TestBase {
    protected WebDriver driver;
    protected MainPage mainPage;

    public void startBrowserFirefox() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        mainPage = new MainPage(driver);
    }

    public void startBrowserChrome() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
    }

    @Before
    public void initBrowser() {
        String browser = System.getProperty("browser", "chrome"); // chrome по умолчанию
        if (browser.equalsIgnoreCase("firefox")) {
            startBrowserFirefox();
        } else {
            startBrowserChrome();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.manage().window().maximize();
        driver.get(BASE_URL);

        // Вызов acceptCookies из MainPage
        mainPage.acceptCookies();

        // Прокрутка к FAQ после загрузки страницы и принятия куки
        mainPage.scrollToFaq();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
