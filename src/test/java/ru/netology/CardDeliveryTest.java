package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {


    @BeforeEach
    void SetUp() {
        Configuration.holdBrowserOpen = true;
        //Configuration.headless = true;
        open("http://localhost:9999/");
    }

    LocalDate today = LocalDate.now();
    int monthToday = today.getMonthValue();
    int yearToday = today.getYear();


    @Test
    void shouldSubmitTheForm() {

        LocalDate date = today.plusDays(5);
        String strDate = date.format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));

        $("input[placeholder='Город']").setValue("Екатеринбург");
        $("input[placeholder='Дата встречи']").sendKeys(Keys.CONTROL + "a");
        $("input[placeholder='Дата встречи']").sendKeys(Keys.DELETE);
        $("input[placeholder='Дата встречи']").setValue(strDate);
        $("input[name='name']").setValue("Слава Чебурашкин");
        $("input[name='phone']").setValue("+79999999999");
        $("label[data-test-id='agreement']").click();
        $x("//span[contains(text(),'Забронировать')]//ancestor::button").click();
        $x("//*[contains(text(),'Успешно')]").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldSubmitTheFormWithChoiceCalendarWeekLater() {
        LocalDate date = today.plusDays(7);
        int days = date.getDayOfMonth();
        int year = date.getYear();
        int month = date.getMonthValue();

        $("input[placeholder='Город']").setValue("Екатеринбург");

        $(".calendar-input__custom-control").click();

        if (month == monthToday && year == yearToday) {
            $x("//td[contains(text(), '" + days + "')]").click();
        } else {
            $(".calendar__arrow_direction_right[data-step='1']").click();
            $x("//td[contains(text(), '" + days + "')]").click();
        }

        $("input[name='name']").setValue("Слава Чебурашкин");
        $("input[name='phone']").setValue("+79999999999");
        $("label[data-test-id='agreement']").click();
        $x("//span[contains(text(),'Забронировать')]//ancestor::button").click();
        $x("//*[contains(text(),'Успешно')]").shouldBe(visible, Duration.ofSeconds(15));

    }

    @Test
    void shouldSubmitTheFormWithChoiceYekaterinburg() {
        LocalDate date = today.plusDays(10);
        String strDate = date.format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));

        $("input[placeholder='Город']").setValue("ка");
        $x("//*[(text()='Казань')]").click();

        $("input[placeholder='Дата встречи']").sendKeys(Keys.CONTROL + "a");
        $("input[placeholder='Дата встречи']").sendKeys(Keys.DELETE);
        $("input[placeholder='Дата встречи']").setValue(strDate);

        $("input[name='name']").setValue("Слава Чебурашкин");
        $("input[name='phone']").setValue("+79999999999");
        $("label[data-test-id='agreement']").click();
        $x("//span[contains(text(),'Забронировать')]//ancestor::button").click();
        $x("//*[contains(text(),'Успешно')]").shouldBe(visible, Duration.ofSeconds(15));

    }


}
