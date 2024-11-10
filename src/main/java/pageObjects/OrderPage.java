package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class OrderPage {
    private WebDriver driver;
    private StepOnePage stepOnePage;
    private StepTwoPage stepTwoPage;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.stepOnePage = new StepOnePage(driver);
        this.stepTwoPage = new StepTwoPage(driver);
    }

    public StepOnePage getStepOnePage() {
        return stepOnePage;
    }

    public StepTwoPage getStepTwoPage() {
        return stepTwoPage;
    }

    //метод для ожидания видимости элемента
    private static void waitForVisibility(WebDriver driver, By locator, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    //метод для скроллинга к элементу
    private static void scrollToElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    //вложенный класс первого шага заказа
    public class StepOnePage {
        private WebDriver driver;
        //локаторы первого шага заказа
        private By orderHeaderStepOne = By.xpath(".//div[starts-with(@class, 'Order_Header') and text()='Для кого самокат']"); //заголовок заказа "Для кого самокат"
        private By nameInput = By.xpath(".//div[starts-with(@class, 'Input_InputContainer')]/input[contains(@placeholder, 'Имя')]"); //имя
        private By surnameInput = By.xpath(".//div[starts-with(@class, 'Input_InputContainer')]/input[contains(@placeholder, 'Фамилия')]");  //фамилия
        private By addressInput = By.xpath(".//div[starts-with(@class, 'Input_InputContainer')]/input[contains(@placeholder, 'Адрес')]"); //адрес покупателя
        private By metroInput = By.xpath(".//div[@class='select-search__value']/input[contains(@placeholder, 'Станция метро')]"); //станция метро покупателя
        private By selectedMetro = By.xpath(".//ul[@class='select-search__options']/li[@class='select-search__row']"); // список станций метро
        private By phoneInput = By.xpath(".//div[starts-with(@class, 'Input_InputContainer')]/input[contains(@placeholder, 'Телефон')]"); //номер телефона
        private By nextButton = By.xpath(".//div[starts-with(@class, 'Order_NextButton')]/button[text()='Далее']"); //кнопка Далее

        public StepOnePage(WebDriver driver) {
            this.driver = driver;
        }
        public boolean isStepOneVisible() {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(orderHeaderStepOne));
            return driver.findElement(orderHeaderStepOne).isDisplayed();
        }

        public void setName(String name) {
            WebElement nameElement = driver.findElement(nameInput);
            nameElement.clear();
            nameElement.sendKeys(name);
        }

        public void setSurname(String surname) {
            WebElement surnameElement = driver.findElement(surnameInput);
            surnameElement.clear();
            surnameElement.sendKeys(surname);
        }

        public void setAddress(String address) {
            WebElement addressElement = driver.findElement(addressInput);
            addressElement.clear();
            addressElement.sendKeys(address);
        }

        public void setMetro(String metro) {
            WebElement metroElement = driver.findElement(metroInput);
            metroElement.clear();
            metroElement.sendKeys(metro);
            //новые методы: для ожидания видимости и скроллинга
            waitForVisibility(driver, selectedMetro, 10);
            WebElement selectedElement = driver.findElement(selectedMetro);
            scrollToElement(driver, selectedElement);
            selectedElement.click();
        }

        public void setPhone(String phone) {
            WebElement phoneElement = driver.findElement(phoneInput);
            phoneElement.clear();
            phoneElement.sendKeys(phone);
        }

        public boolean isStepOneDataValid() {
            //проверяем, что данные первого шага были заполнены
            return !driver.findElement(nameInput).getAttribute("value").isEmpty() &&
                    !driver.findElement(surnameInput).getAttribute("value").isEmpty() &&
                    !driver.findElement(addressInput).getAttribute("value").isEmpty() &&
                    !driver.findElement(phoneInput).getAttribute("value").isEmpty();
        }

        public void clickNext() {
            driver.findElement(nextButton).click();
        }
    }
    //вложенный класс для второго шага заказа
    public class StepTwoPage {
        private WebDriver driver;
        //локаторы второго шага
        private By orderHeaderStepTwo = By.xpath(".//div[starts-with(@class, 'Order_Header') and text()='Про аренду']"); //заголовок "Про аренду"
        private By dateInput = By.xpath(".//div[@class='react-datepicker-wrapper']//input[contains(@placeholder, 'Когда привезти самокат')]"); //дата
        private By selectedDate = By.xpath(".//div[contains(@class, 'react-datepicker__day--selected')]");
        private By rentalPeriodInput = By.xpath(".//div[@class='Dropdown-control']/div[contains(text(), 'Срок аренды')]"); //срок аренды
        private By rentalPeriodDropDown = By.xpath(".//div[@class='Dropdown-menu']"); //выпадающий список срок аренды
        private By rentalDropDownOption = By.xpath(".//div[@class='Dropdown-option']");
        private By blackCheckBox = By.xpath(".//div[starts-with(@class, 'Order_Checkboxes')]/label[@for='black']"); //чекбокс "чёрный жемчуг"
        private By greyCheckBox = By.xpath(".//div[starts-with(@class, 'Order_Checkboxes')]/label[@for='grey']"); //чекбокс "серая безысходность"
        private By commentInput = By.xpath(".//div[starts-with(@class, 'Input_InputContainer')]/input[@placeholder='Комментарий для курьера']"); //комментарий
        private By orderButtonDown = By.xpath(".//div[starts-with(@class, 'Order_Buttons')]/button[text()='Заказать'] "); //кнопка Заказать
        //локаторы окна "Хотите оформить заказ?"
        private By modalHeader = By.xpath(".//div[starts-with(@class, 'Order_Modal')]/div[text()='Хотите оформить заказ?']"); //заголовок
        private By modalYesButton = By.xpath(".//div[starts-with(@class, 'Order_Modal')]//button[text()='Да']"); //кнопка Да
        private By orderCompleted = By.xpath(".//div[starts-with(@class, 'Order_ModalHeader') and text()='Заказ оформлен']"); //сообщение "Заказ оформлен"

        public StepTwoPage(WebDriver driver) {
            this.driver = driver;
        }

        public boolean isStepTwoVisible() {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(orderHeaderStepTwo));
            return driver.findElement(orderHeaderStepTwo).isDisplayed();
        }

        public void setDate(String date) {
            WebElement dateElement = driver.findElement(dateInput);
            dateElement.clear();
            dateElement.sendKeys(date);
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(selectedDate));
            WebElement selectedElement = driver.findElement(selectedDate);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", selectedElement);
            selectedElement.sendKeys(Keys.ENTER);
        }

        public void chooseRentalPeriod(String period) {
            driver.findElement(rentalPeriodInput).click();
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(rentalPeriodDropDown));
            List<WebElement> options = driver.findElement(rentalPeriodDropDown).findElements(rentalDropDownOption);
            for (WebElement option : options) {
                if (option.getText().equals(period)) {
                    option.click();
                    break;
                }
            }
        }

        public void selectBlackCheckbox() {
            driver.findElement(blackCheckBox).click();
        }

        public void selectGreyCheckbox() {
            driver.findElement(greyCheckBox).click();
        }

        public void setComment(String comment) {
            WebElement commentElement = driver.findElement(commentInput);
            commentElement.clear();
            commentElement.sendKeys(comment);
        }

        public void clickOrderButton() {
            driver.findElement(orderButtonDown).click();
        }

        public boolean isModalVisible() {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(modalHeader));
            return driver.findElement(modalHeader).isDisplayed();
        }

        public void confirmOrder() {
            driver.findElement(modalYesButton).click();
        }

        public boolean isOrderCompleted() {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(orderCompleted));
            return driver.findElement(orderCompleted).isDisplayed();
        }

        public boolean isStepTwoDataValid() {
            return !driver.findElement(dateInput).getAttribute("value").isEmpty() &&
                    driver.findElement(rentalPeriodInput).getText() != null;
        }

    }
}