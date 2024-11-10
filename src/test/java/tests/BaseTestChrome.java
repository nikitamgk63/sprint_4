package tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import pageObjects.MainPage;
public abstract class BaseTestChrome {

    WebDriver driver;
    WebDriverWait wait;
    MainPage faqPage;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-search-engine-choice-screen");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://qa-scooter.praktikum-services.ru/");
        faqPage = new MainPage(driver);

        //подтверждаем куки
        WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("rcc-confirm-button")));
        cookieButton.click();
    }

    //закрываем браузер
    @After
    public void tearDown() {
        driver.quit();
    }
}