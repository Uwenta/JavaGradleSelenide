package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {


    @BeforeEach
    void SetUp() {
        //Configuration.holdBrowserOpen=true;
        Configuration.headless = true;
        open("http://localhost:9999/");

    }


    @Test
    void shouldSubmitTheForm() {

        LocalDate today = LocalDate.now();
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


}
