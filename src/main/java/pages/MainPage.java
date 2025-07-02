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

    // Локаторы
    // Кнопка заказать вверху страницы
    private final By orderButtonTop = By.xpath("//button[contains(@class, 'Button_Button__ra12g') and text()='Заказать']");
    // Кнопка заказать внизу страницы
    private final By orderButtonBottom = By.xpath("(//button[contains(@class, 'Button_Middle__1CSJM') and text()='Заказать'])[2]");
    // Клопка принятия куки
    private final By cookieButton = By.id("rcc-confirm-button");
    // Поле вопросов FAQ
    private final By faqSection = By.className("Home_FAQ__3uVm4");
    // Кнопка статуса заказа
    private final By orderStatusButton = By.xpath("//button[text()='Статус заказа']");
    // Кнопка введения номера заказа
    private final By orderNumberInput = By.xpath("//input[@placeholder='Введите номер заказа']");
    // Кнопка Go
    private final By goButton = By.xpath("//button[text()='Go!']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Основные методы для заказа
    public void clickOrderButtonTop() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(orderButtonTop));
        scrollAndClick(button);
    }

    public void clickOrderButtonBottom() {
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(orderButtonBottom));
        scrollToElement(button);
        wait.until(ExpectedConditions.elementToBeClickable(button)).click();
    }

    // Методы для работы с куки
    public void acceptCookies() {
        try {
            WebElement cookieBanner = wait.until(ExpectedConditions.elementToBeClickable(cookieButton));
            cookieBanner.click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(cookieButton));
        } catch (Exception e) {
            System.out.println("Cookie banner not found or already accepted");
        }
    }

    // Методы для проверки статуса заказа
    public void checkOrderStatus(String orderNumber) {
        clickOrderStatusButton();
        enterOrderNumber(orderNumber);
        clickGoButton();
    }

    private void clickOrderStatusButton() {
        wait.until(ExpectedConditions.elementToBeClickable(orderStatusButton)).click();
    }

    private void enterOrderNumber(String orderNumber) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(orderNumberInput));
        input.clear();
        input.sendKeys(orderNumber);
    }

    private void clickGoButton() {
        wait.until(ExpectedConditions.elementToBeClickable(goButton)).click();
    }

    // Методы для FAQ
    public void clickQuestion(int index) {
        By specificQuestion = By.id("accordion__heading-" + index);
        WebElement question = wait.until(ExpectedConditions.elementToBeClickable(specificQuestion));
        scrollAndClick(question);
    }

    public String getAnswerText(int index) {
        By answer = By.id("accordion__panel-" + index);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(answer)).getText();
    }

    // Вспомогательные методы
    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                element
        );
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void scrollAndClick(WebElement element) {
        scrollToElement(element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    // Дополнительные проверки
    public boolean isOrderButtonTopDisplayed() {
        try {
            return driver.findElement(orderButtonTop).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOrderButtonBottomDisplayed() {
        try {
            return driver.findElement(orderButtonBottom).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
