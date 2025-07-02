package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локатор для поля имя
    private By nameField = By.xpath("//input[@placeholder='* Имя']");
    // Локатор для поля фамилия
    private By surnameField = By.xpath("//input[@placeholder='* Фамилия']");
    // Локатор для поля адрес
    private By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    // Локатор для поля выбора станции метро
    private By metroField = By.xpath("//input[@placeholder='* Станция метро']");
    // Локатор для поля телефон
    private By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    // Локатор для кнопки далее
    private By nextButton = By.xpath("//button[text()='Далее']");
    // Локатор для поля выбора даты
    private By dateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    // Локатор для поля выбора длительности аренды
    private By rentPeriodDropdown = By.className("Dropdown-placeholder");
    // Локатор для поля выбора цвета самоката
    // Локатор для поля "Черный жемчуг"
    private By colorCheckboxBlack = By.id("black");
        // Локатор для поля "Серая безысходность"
    private By colorCheckboxGrey = By.id("grey");
    // Локатор для поля комментария курьеру
    private By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");

    // Кнопки "Заказать":
    // Копка "заказать" в верхней части страницы
    private By orderButtonTop = By.xpath("//button[contains(@class, 'Button_Button__ra12g') and not(contains(@class, 'Button_Middle__1CSJM')) and text()='Заказать']");
    // Кнопка "заказать" в нижней части страницы
    private By orderButtonBottom = By.xpath("//button[contains(@class, 'Button_Button__ra12g') and contains(@class, 'Button_UltraBig__UU3Lp') and text()='Заказать']");
    // Кнопка "заказать" в середине страницы, в конце формы заказа самоката
    private By orderButtonMiddle = By.xpath("//button[contains(@class, 'Button_Button__ra12g') and contains(@class, 'Button_Middle__1CSJM') and text()='Заказать']");
    // Кнопка подтверждения заказа самоката
    private By confirmYesButton = By.xpath("//button[text()='Да']");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void fillFirstOrderPage(String name, String surname, String address, String metro, String phone) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);
        driver.findElement(surnameField).sendKeys(surname);
        driver.findElement(addressField).sendKeys(address);
        driver.findElement(metroField).click();
        driver.findElement(metroField).sendKeys(metro);
    // Подождать и выбрать первую станцию метро из автодополнения
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'select-search__select')]//button"))).click();
        driver.findElement(phoneField).sendKeys(phone);
    }

    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }

    public void fillSecondOrderPage(String date, int rentPeriodIndex, String color, String comment) {
        WebElement dateInput = wait.until(ExpectedConditions.elementToBeClickable(dateField));
        dateInput.sendKeys(date);
        dateInput.sendKeys(Keys.ENTER);

        WebElement rentDropdown = driver.findElement(rentPeriodDropdown);
        rentDropdown.click();

        WebElement rentOption = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//div[@class='Dropdown-menu']/div"))).get(rentPeriodIndex);
        rentOption.click();

        if ("black".equalsIgnoreCase(color)) {
            driver.findElement(colorCheckboxBlack).click();
        } else if ("grey".equalsIgnoreCase(color)) {
            driver.findElement(colorCheckboxGrey).click();
        }

        WebElement commentInput = driver.findElement(commentField);
        commentInput.sendKeys(comment);
        commentInput.sendKeys(Keys.ENTER);
    }

    // Методы для нажатия кнопок "Заказать" в разных местах страницы
    public void clickOrderButtonTop() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(orderButtonTop));
        btn.click();
    }

    public void clickOrderButtonBottom() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(orderButtonBottom));
        btn.click();
    }

    public void clickOrderButtonMiddle() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(orderButtonMiddle));
        btn.click();
    }

    public void confirmOrder() {
        WebElement yesButton = wait.until(ExpectedConditions.elementToBeClickable(confirmYesButton));
        yesButton.click();
    }
}
