package ru.netology;

import com.codeborne.selenide.Condition;
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
        //Configuration.headless = true;
        open("http://localhost:9999/");
    }

    LocalDate today = LocalDate.now();


    public String generateDate(int days) {
        return today.plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    @Test
    void shouldSubmitTheForm() {
        String strDate = generateDate(5);

        $("input[placeholder='Город']").setValue("Екатеринбург");
        $("input[placeholder='Дата встречи']").sendKeys(Keys.CONTROL + "a");
        $("input[placeholder='Дата встречи']").sendKeys(Keys.DELETE);
        $("input[placeholder='Дата встречи']").setValue(strDate);
        $("input[name='name']").setValue("Слава Чебурашкин");
        $("input[name='phone']").setValue("+79999999999");
        $("label[data-test-id='agreement']").click();
        $x("//span[contains(text(),'Забронировать')]//ancestor::button").click();
        $x("//*[contains(text(),'Успешно')]").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + strDate), Duration.ofSeconds(15))
                .shouldBe(visible);
    }

    @Test
    void shouldSubmitTheFormWithChoiceCalendarWeekLater() {
        LocalDate date = today.plusDays(7);
        int days = date.getDayOfMonth();
        int year = date.getYear();
        int month = date.getMonthValue();
        String strDate = generateDate(7);

        $("input[placeholder='Город']").setValue("ка");
        $x("//span[(text()='Казань')]").click();
        $(".calendar-input__custom-control").click();
        if (month == today.getMonthValue() && year == today.getYear()) {
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
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + strDate), Duration.ofSeconds(15))
                .shouldBe(visible);

    }

}
