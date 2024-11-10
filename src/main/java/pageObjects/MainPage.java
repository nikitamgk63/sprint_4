package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

public class MainPage {
    private WebDriver driver;

    //верхяя кнопка Заказать
    private By orderButtonUp = By.xpath(".//div[starts-with(@class, 'Header_Nav')]/button[text()='Заказать']");

    //нижняя кнопка Заказать
    private By orderButtonDown = By.xpath(".//div[starts-with(@class, 'Home_FinishButton')]/button[text()='Заказать']");

    //локатор Вопросов
    private  By[] questionLocators = {
            By.id("accordion__heading-0"),
            By.id("accordion__heading-1"),
            By.id("accordion__heading-2"),
            By.id("accordion__heading-3"),
            By.id("accordion__heading-4"),
            By.id("accordion__heading-5"),
            By.id("accordion__heading-6"),
            By.id("accordion__heading-7")
    };
    //локатор Ответов
    private By[] answerLocators = {
            By.id("accordion__panel-0"),
            By.id("accordion__panel-1"),
            By.id("accordion__panel-2"),
            By.id("accordion__panel-3"),
            By.id("accordion__panel-4"),
            By.id("accordion__panel-5"),
            By.id("accordion__panel-6"),
            By.id("accordion__panel-7")
    };

    public MainPage(WebDriver driver){
        this.driver = driver;
    }

    //метод клика на галочку Вопроса
    public void clickOnQuestion(int index){
        driver.findElement(questionLocators[index]).click();
    }

    //метод получения текста Ответа
    public String getAnswerText(int index) {
        return driver.findElement(answerLocators[index]).getText();
    }

    //метод клика на верхнюю кнопку Заказать
    public OrderPage clickOrderButtonUp() {
        driver.findElement(orderButtonUp).click();
        return new OrderPage(driver);
    }

    //метод клика на нижнюю кнопку Заказать
    public OrderPage clickOrderButtonDown() {
        driver.findElement(orderButtonDown).click();
        return new OrderPage(driver);
    }
}

