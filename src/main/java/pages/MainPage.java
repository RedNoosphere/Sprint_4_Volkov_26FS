package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // URL сайта как константа
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    // Локаторы
    private final By cookieButton = By.id("rcc-confirm-button");
    private final By faqSection = By.className("Home_FAQ__3uVm4");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    // Открыть главную страницу
    public void open() {
        driver.get(BASE_URL);
    }

    // Метод принятия куки — проверяет и принимает, если баннер есть
    public void acceptCookies() {
        try {
            WebElement cookieBanner = wait.until(ExpectedConditions.elementToBeClickable(cookieButton));
            cookieBanner.click();
            wait.until(ExpectedConditions.invisibilityOf(cookieBanner));
            Thread.sleep(100); // небольшая пауза после клика
        } catch (Exception e) {
            System.out.println("Cookie не найдены или уже приняты");
        }
    }

    // Обновлённый метод прокрутки к FAQ — сначала принимает куки
    public void scrollToFaq() {
        acceptCookies();
        WebElement faq = wait.until(ExpectedConditions.visibilityOfElementLocated(faqSection));
        scrollToElement(faq);
    }

    public void clickQuestion(int index) {
        scrollToFaq();
        By questionLocator = By.id("accordion__heading-" + index);
        WebElement question = wait.until(ExpectedConditions.elementToBeClickable(questionLocator));
        scrollAndClick(question);
    }

    public String getAnswerText(int index) {
        By answerLocator = By.id("accordion__panel-" + index);

        wait.until(driver -> {
            WebElement elem = driver.findElement(answerLocator);
            String hiddenAttr = elem.getAttribute("hidden");
            return elem.isDisplayed() && (hiddenAttr == null || hiddenAttr.equals("false"));
        });

        return driver.findElement(answerLocator).getText();
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'start'});", element);
        try {
            Thread.sleep(100);  // небольшая пауза для отрисовки
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void scrollAndClick(WebElement element) {
        scrollToElement(element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
}
