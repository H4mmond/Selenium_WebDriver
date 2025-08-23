package academit.lessonfive;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;


public class PracticePartTwoTests {
    @Test
    public void chooseOptionTest() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("https://the-internet.herokuapp.com/dropdown");

        WebElement selectDropdown = driver.findElement(By.xpath("//*[@id='dropdown']"));
        Select select = new Select(selectDropdown);

        select.selectByVisibleText("Option 2");

        Assertions.assertEquals("2", selectDropdown.getAttribute("value"));

        driver.quit();
    }
}
