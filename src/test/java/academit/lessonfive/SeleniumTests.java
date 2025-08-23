package academit.lessonfive;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SeleniumTests {
    @Test
    public void openYa() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("https://ya.ru");

        String actualURL = driver.getCurrentUrl();
        System.out.println("Actual URL = " + actualURL);

        Thread.sleep(5000);

        Assertions.assertTrue(driver.getCurrentUrl().contains("https://ya.ru"),
                "Expected URL contains https:ya.ru but actual is not");
        driver.quit();
    }

    @Test
    public void checkDropdownTitle() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("https://the-internet.herokuapp.com/dropdown");

        WebElement titleElement = driver.findElement(By.xpath("//*[@class='example']//h3"));
        Assertions.assertTrue(titleElement.isDisplayed());

        Thread.sleep(3000);
        String titleText = titleElement.getText();
        Assertions.assertEquals("Dropdown List", titleText);

        driver.quit();
    }

    @Test
    public void checkNewsListSizeTest() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("https://mail.ru/");
        driver.manage().window().maximize();

        List<WebElement> newsList = driver.findElements(By.xpath("//ul[contains(@class,'tabs-content')]//li"));
        Assertions.assertFalse(newsList.isEmpty());
        Assertions.assertEquals(15, newsList.size());

//        List<String> expectedResults = List.of("Text1", "Text2");
//        for (int i = 0; i < newsList.size(); ++i) {
//            Assertions.assertEquals(expectedResults.get(i), newsList.get(i).getText(), "Error message");
//        }

        for (WebElement newsItem: newsList) {
            Assertions.assertFalse(newsItem.getText().isEmpty());
        }

        driver.quit();
    }

    @Test
    public void checkArrows() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("https://the-internet.herokuapp.com/inputs");
        WebElement input = driver.findElement(By.xpath("//input[@type='number']"));
        input.sendKeys(Keys.ARROW_UP);
        input.sendKeys(Keys.ARROW_UP);
        Thread.sleep(3000);
        Assertions.assertEquals("2", input.getAttribute("value"));

        driver.quit();
    }

    @Test
    public void checkAcademitsRadioButtonTest() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("https://academ-it-school.ru/payment?course=java_begin");
        driver.manage().window().maximize();

        WebElement testingRadioButton = driver.findElement(By.cssSelector("input[name='CourseType'][value='Testing']"));
        testingRadioButton.click();

        Thread.sleep(2000);

        Assertions.assertEquals("true", testingRadioButton.getAttribute("checked"));

        driver.quit();
    }

    @Test
    public void selectTest() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("https://htmlbook.ru/html/select");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        WebElement selectDropdown = driver.findElement(By.cssSelector("[name='select2']"));
        Select select = new Select(selectDropdown);

        select.selectByVisibleText("Шапокляк");
        List<WebElement> allOptions = select.getOptions();
        System.out.println("All options: ");
        for (WebElement item : allOptions) {
            System.out.println(item.getText());
        }

        Thread.sleep(2000);
        Assertions.assertEquals("Шапокляк", selectDropdown.getAttribute("value"));
        Assertions.assertEquals("Выберите героя", allOptions.get(0).getText());
        Assertions.assertEquals("Чебурашка", allOptions.get(1).getText());
        Assertions.assertEquals("Крокодил Гена", allOptions.get(2).getText());
        Assertions.assertEquals("Крыса Лариса", allOptions.get(4).getText());

        driver.quit();
    }

}
