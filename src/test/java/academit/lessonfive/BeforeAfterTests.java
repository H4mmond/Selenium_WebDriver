package academit.lessonfive;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BeforeAfterTests {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.get("https://the-internet.herokuapp.com/");
        driver.manage().window().maximize();
    }

    @Test
    public void selectHerokuTest() {
        Assertions.assertEquals("https://the-internet.herokuapp.com/", driver.getCurrentUrl());
    }

    @Test
    public void checkTitleHerokuTest() {
        Assertions.assertTrue(driver.getTitle().contains("Internet"));
        Assertions.assertTrue(driver.getTitle().equals("The Internet"));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

}
