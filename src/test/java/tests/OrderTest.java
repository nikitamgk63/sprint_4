package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pageObjects.OrderPage;
import pageObjects.MainPage;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest extends BaseTestChrome {

    private OrderPage orderPage;

    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final String date;
    private final String period;
    private final String comment;

    public OrderTest(String name, String surname, String address, String metro, String phone, String date, String period, String comment) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.period = period;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] orderButton() {
        return new Object[][]{
                {"Катя", "Пушкарева", "Ленина", "Динамо", "89567418596", "28.06.2023", "сутки", "оставить у двери"},
                {"Миша", "Потапов", "Маркса дом 5", "Цветной бульвар", "+79137789635", "22.08.2023", "пятеро суток", "Привет!"}
        };
    }

    @Test
    public void checkOrderByUpButton() {
        MainPage mainPage = new MainPage(driver);
        orderPage = mainPage.clickOrderButtonUp();
        ordering(orderPage);
    }

    @Test
    public void checkOrderByDownButton() {
        MainPage mainPage = new MainPage(driver);
        orderPage = mainPage.clickOrderButtonDown();
        ordering(orderPage);
    }

    private void ordering(OrderPage orderPage) {
        //проверка, что мы на первом этапе заказа
        assertTrue(orderPage.getStepOnePage().isStepOneVisible());

        //заполнение полей первого шага
        orderPage.getStepOnePage().setName(name);
        orderPage.getStepOnePage().setSurname(surname);
        orderPage.getStepOnePage().setAddress(address);
        orderPage.getStepOnePage().setMetro(metro);
        orderPage.getStepOnePage().setPhone(phone);

        //переход на второй шаг
        orderPage.getStepOnePage().clickNext();
        assertTrue(orderPage.getStepTwoPage().isStepTwoVisible());

        //заполнение полей второго шага
        orderPage.getStepTwoPage().setDate(date);
        orderPage.getStepTwoPage().chooseRentalPeriod(period);
        orderPage.getStepTwoPage().selectBlackCheckbox();
        orderPage.getStepTwoPage().setComment(comment);

        //подтверждение заказа
        orderPage.getStepTwoPage().clickOrderButton();
        assertTrue(orderPage.getStepTwoPage().isModalVisible());
        orderPage.getStepTwoPage().confirmOrder();
        assertTrue(orderPage.getStepTwoPage().isOrderCompleted());
    }
}
