package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локаторы полей и кнопок
    private By nameField = By.xpath("//input[@placeholder='* Имя']");
    private By surnameField = By.xpath("//input[@placeholder='* Фамилия']");
    private By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private By metroField = By.xpath("//input[@placeholder='* Станция метро']");
    private By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private By nextButton = By.xpath("//button[text()='Далее']");
    private By dateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private By rentPeriodDropdown = By.className("Dropdown-placeholder");
    private By colorCheckboxBlack = By.id("black");
    private By colorCheckboxGrey = By.id("grey");
    private By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");

    private By orderButtonTop = By.xpath("//button[contains(@class, 'Button_Button__ra12g') and not(contains(@class, 'Button_Middle__1CSJM')) and text()='Заказать']");
    private By orderButtonBottom = By.xpath("//button[contains(@class, 'Button_Button__ra12g') and contains(@class, 'Button_UltraBig__UU3Lp') and text()='Заказать']");
    private By orderButtonMiddle = By.xpath("//button[contains(@class, 'Button_Button__ra12g') and contains(@class, 'Button_Middle__1CSJM') and text()='Заказать']");
    private By confirmYesButton = By.xpath("//button[contains(@class, 'Button_Button__ra12g') and contains(@class, 'Button_Middle__1CSJM') and text()='Да']");
    private By orderModal = By.cssSelector("div.Order_Modal__YZ-d3");

    private By statusButton = By.xpath("//button[contains(text(),'Посмотреть статус')]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillFirstOrderPage(String name, String surname, String address, String metro, String phone) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);
        driver.findElement(surnameField).sendKeys(surname);
        driver.findElement(addressField).sendKeys(address);
        driver.findElement(metroField).click();
        driver.findElement(metroField).sendKeys(metro);
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
        // Ждем появления модального окна с подтверждением заказа
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderModal));

        // После появления окна — находим кнопку "Да" внутри модального окна
        WebElement yesButton = wait.until(ExpectedConditions.elementToBeClickable(confirmYesButton));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", yesButton);

        try {
            Thread.sleep(500); // Пауза 500 мс перед кликом
            yesButton.click();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ElementNotInteractableException e) {
            try {
                Thread.sleep(500); // Пауза перед повторным кликом
                yesButton.click();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public String waitForStatusButtonAndGetText() {
        WebElement statusBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(statusButton));
        return statusBtn.getText();
    }
}
