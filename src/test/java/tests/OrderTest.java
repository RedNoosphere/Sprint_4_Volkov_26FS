package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import pages.OrderPage;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class OrderTest {
    private WebDriver driver;
    private WebDriverWait wait;

    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final String date;
    private final int rentPeriodIndex;
    private final String color;
    private final String comment;
    private final boolean useTopButton;

    private MainPage mainPage;
    private OrderPage orderPage;

    public OrderTest(String name, String surname, String address, String metro, String phone,
                     String date, int rentPeriodIndex, String color, String comment,
                     boolean useTopButton) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.rentPeriodIndex = rentPeriodIndex;
        this.color = color;
        this.comment = comment;
        this.useTopButton = useTopButton;
    }

    @Parameterized.Parameters(name = "{9} кнопка, {0} {1}")
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][] {
                {"Иван", "Иванов", "ул. Пушкина, д.1", "Лубянка", "+79991112233", "01.07.2025", 0, "black", "Комментарий 1", true},
                {"Петр", "Петров", "ул. Лермонтова, д.5", "Тверская", "+79994445566", "02.07.2025", 1, "grey", "Комментарий 2", false},
        });
    }

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();;
        // options.setHeadless(true); // если нужен безголовый режим

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        mainPage = new MainPage(driver);
        orderPage = new OrderPage(driver);

        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @Test
    public void testOrderCreation() {
        mainPage.acceptCookies();

        if (useTopButton) {
            orderPage.clickOrderButtonTop();
        } else {
            orderPage.clickOrderButtonBottom();
        }

        orderPage.fillFirstOrderPage(name, surname, address, metro, phone);
        orderPage.clickNextButton();
        orderPage.fillSecondOrderPage(date, rentPeriodIndex, color, comment);

        orderPage.clickOrderButtonMiddle();

        orderPage.confirmOrder();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
