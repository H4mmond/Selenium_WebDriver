package academit.fifthwheel;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OnlineStoreTests {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private String mainURL = "https://demowebshop.tricentis.com/";

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30), Duration.ofMillis(500));

        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.get(mainURL);
        driver.manage().window().maximize();
        Assertions.assertEquals(mainURL, driver.getCurrentUrl(),
                "Expected URL is " + mainURL + " but actual is " + driver.getCurrentUrl());
        Assertions.assertEquals("Demo Web Shop", driver.getTitle(),
                "Expected title is 'Demo Web Shop', but actual is " + driver.getTitle());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @ParameterizedTest
//    @ValueSource(strings = {"Laptop"})
    @ValueSource(strings = {"Laptop", "Smartphone", "Fiction"})
    void parameterizedSearch(String product) throws InterruptedException {
//        WebElement linkToCart = driver.findElement(By.id("topcartlink"));
//        WebElement productCounter = linkToCart.findElement(By.className("cart-qty"));
//        Assertions.assertEquals("(0)", productCounter.getText());

        WebElement searchInput = driver.findElement(By.id("small-searchterms"));
        searchInput.sendKeys(product);
        Assertions.assertEquals(product, searchInput.getAttribute("value"),
                "Expected search text is " + product + " but actual is " + searchInput.getAttribute("value"));
        searchInput.sendKeys(Keys.RETURN);

        wait.until(ExpectedConditions.urlToBe(mainURL + "search?q=" + product));
        Assertions.assertEquals(mainURL + "search?q=" + product, driver.getCurrentUrl(),
                "Exprected URL is " + mainURL + "search?q=" + product + " but actual is " + driver.getCurrentUrl());

        WebElement productItem = driver.findElement(By.xpath("//*[@class='product-item' and .//*[@value='Add to cart']]"));
        String productName = productItem.findElement(By.className("product-title")).getText();
        System.out.println(productName);

        WebElement btnAddToCart = productItem.findElement(By.className("product-box-add-to-cart-button"));
        Assertions.assertEquals("Add to cart", btnAddToCart.getAttribute("value"));
        btnAddToCart.click();

//        wait.until(ExpectedConditions.textToBePresentInElement(productCounter, "(1)"));
        Thread.sleep(1000);
        driver.findElement(By.id("topcartlink")).click();

        wait.until(ExpectedConditions.urlToBe(mainURL + "cart"));
        Assertions.assertEquals(mainURL + "cart", driver.getCurrentUrl(),
                "Exprected URL is " + mainURL + "cart" + " but actual is " + driver.getCurrentUrl());

        List<WebElement> cartProductList = driver.findElements(By.xpath("//table[@class='cart']//tbody//tr"));
        Assertions.assertEquals(1, cartProductList.size(),
                "Expected number of unique products is '1' but actual is " + cartProductList.size());

        WebElement cartProductName = driver.findElement(By.xpath("//*[@class='cart-item-row']//*[@class='product-name']"));
        Assertions.assertEquals(productName, cartProductName.getText(), "Expected product name in your cart is " + productName + " but actual is " + cartProductName.getText());
    }
}
