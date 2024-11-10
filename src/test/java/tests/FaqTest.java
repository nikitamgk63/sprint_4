package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pageObjects.OrderPage;
import pageObjects.MainPage;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class FaqTest extends BaseTestChrome {
    private final String question;
    private final String expectedAnswer;
    private final int questionIndex;

    //параметры для каждого теста
    public FaqTest(String question, String expectedAnswer, int questionIndex) {
        this.question = question;
        this.expectedAnswer = expectedAnswer;
        this.questionIndex = questionIndex;
    }

    //данные для для тестов (вопросы, ответы и индексы)
    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {"Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой.", 0},
                {"Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.", 1},
                {"Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.", 2},
                {"Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее.", 3},
                {"Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.", 4},
                {"Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.", 5},
                {"Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.", 6},
                {"Я жизу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области.", 7}
        };
    }

    @Test
    public void testFaqDropdown() {

        MainPage mainPage = new MainPage(driver);
        //нажимаем на вопрос
        mainPage.clickOnQuestion(questionIndex);

        //проверка, что нужный ответ отображается
        String answerText = mainPage.getAnswerText(questionIndex);
        assertTrue("Ответ для вопроса '" + question + "' должен быть: " + expectedAnswer, answerText.contains(expectedAnswer));
    }
}