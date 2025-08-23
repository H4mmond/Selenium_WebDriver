package academit.fifthwheel;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderFormTests {
    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30), Duration.ofMillis(500));

        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.get("https://formdesigner.ru/examples/order.html");
        driver.manage().window().maximize();
        Assertions.assertEquals("https://formdesigner.ru/examples/order.html", driver.getCurrentUrl(),
                "Expected URL is 'https://formdesigner.ru/examples/order.html', but actual is " + driver.getCurrentUrl());
        Assertions.assertEquals("Форма заказа товара для сайта!", driver.getTitle(),
                "Expected title is 'Форма заказа товара для сайта!', but actual is " + driver.getTitle());
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @Test
    @Order(1)
    public void dialogCookie() {
        Assertions.assertEquals("Этот сайт использует файлы cookie!", driver.findElement(By.id("c-ttl")).getText(),
                "Expected dialog header is 'Этот сайт использует файлы cookie!' but atual is " + driver.findElement(By.id("c-ttl")).getText());

        WebElement btnAcceptCookie = driver.findElement(By.id("c-p-bn"));
        Assertions.assertEquals("Принять все", btnAcceptCookie.getText(),
                "Expected button name is 'Принять все' but actual is " + btnAcceptCookie.getText());

        btnAcceptCookie.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("c-inr")));
    }

    @Test
    @Order(2)
    public void exampleForm() {
        WebElement exampleFormTitle = driver.findElement(By.xpath("//*[normalize-space()='Пример готовой формы']"));
        TestUtils.scrollToElement(driver, exampleFormTitle);

        WebElement iframe = driver.findElement(By.xpath("//iframe[contains(@id,'form1006')]"));
        driver.switchTo().frame(iframe);

        WebElement btnSendForm = driver.findElement(By.className("send"));
        Assertions.assertEquals("Отправить", btnSendForm.getText(),
                "Expected button text is 'Отправить' but actual is " + btnSendForm.getText());
        btnSendForm.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("errorSummary")));
        List<WebElement> errorSummary = driver.findElements(By.xpath("//*[contains(@class,'errorSummary')]//li"));
        List<String> expectedErrorSummary = List.of("Необходимо заполнить поле ФИО:.", "Необходимо заполнить поле E-mail.", "Необходимо заполнить поле Количество.", "Необходимо заполнить поле Дата доставки.");
        for (int i = 0; i < errorSummary.size(); ++i) {
            Assertions.assertEquals(expectedErrorSummary.get(i), errorSummary.get(i).getText(),
                    "Expected field error is " + expectedErrorSummary.get(i) + " but actual is " + errorSummary.get(i).getText());
        }
    }
}
