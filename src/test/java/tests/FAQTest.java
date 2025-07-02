package tests;

import org.junit.Test;
import pages.MainPage;
import utils.TestBase;
import static org.junit.Assert.assertTrue;

public class FAQTest extends TestBase {
    @Test
    public void testFirstQuestion() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickQuestion(0);
        String answer = mainPage.getAnswerText(0);
        assertTrue("Ответ не содержит ожидаемого текста",
                answer.contains("Сутки — 400 рублей"));
    }

    @Test
    public void testSecondQuestion() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickQuestion(1);
        String answer = mainPage.getAnswerText(1);
        assertTrue("Ответ не содержит ожидаемого текста",
                answer.contains("Пока что у нас так"));
    }
}