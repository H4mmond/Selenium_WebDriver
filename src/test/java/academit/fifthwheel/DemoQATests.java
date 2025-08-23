package academit.fifthwheel;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DemoQATests {
    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        String browser = System.getProperty("browser");
        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }

        wait = new WebDriverWait(driver, Duration.ofSeconds(30), Duration.ofMillis(500));

        driver.manage().timeouts().pageLoadTimeout(80, TimeUnit.SECONDS);
        driver.get("https://demoqa.com/automation-practice-form");
        driver.manage().window().maximize();
        Assertions.assertEquals("https://demoqa.com/automation-practice-form", driver.getCurrentUrl(),
                "Expected URL is https://demoqa.com/automation-practice-form but actual is " + driver.getCurrentUrl());
        Assertions.assertEquals("DEMOQA", driver.getTitle(),
                "Expected title is 'DEMOQA' but actual is " + driver.getTitle());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void practiceFormTest() throws InterruptedException {
        WebElement form = driver.findElement(By.id("userForm"));

        //        field titles
        List<WebElement> fieldTitle = form.findElements(By.className("col-md-3"));
        List<String> expectedFieldTitle = List.of("Name", "Email", "Gender", "Mobile(10 Digits)", "Date of Birth", "Subjects", "Hobbies", "Picture", "Current Address", "State and City");
        for (int i = 0; i < fieldTitle.size(); ++i) {
            Assertions.assertEquals(expectedFieldTitle.get(i), fieldTitle.get(i).getText(),
                    "Expected field title is '" + expectedFieldTitle.get(i) + "' but actual is " + fieldTitle.get(i).getText());
        }

        //        FIRST NAME
        WebElement firstName = driver.findElement(By.id("firstName"));
        Assertions.assertEquals("First Name", firstName.getAttribute("placeholder"),
                "Expected first name placeholder is 'First Name' but actual is " + firstName.getAttribute("placeholder"));

        firstName.sendKeys("first name");
        Assertions.assertEquals("first name", firstName.getAttribute("value"),
                "Expected first name is 'first name' but actual is " + firstName.getAttribute("value"));

        //        LAST NAME
        WebElement lastName = driver.findElement(By.id("lastName"));
        Assertions.assertEquals("Last Name", lastName.getAttribute("placeholder"),
                "Expected last name placeholder is 'Last Name' but actual is " + lastName.getAttribute("placeholder"));

        lastName.sendKeys("last name");
        Assertions.assertEquals("last name", lastName.getAttribute("value"),
                "Expected last name is 'last name' but actual is " + lastName.getAttribute("value"));

        //        EMAIL
        WebElement email = driver.findElement(By.id("userEmail"));
        Assertions.assertEquals("name@example.com", email.getAttribute("placeholder"),
                "Expected email placeholder is 'name@example.com' but actual is " + email.getAttribute("placeholder"));

        email.sendKeys("user322@gmail.com");
        Assertions.assertEquals("user322@gmail.com", email.getAttribute("value"),
                "Expected email is 'user322@gmail.com' but actual is " + email.getAttribute("value"));

        //        GENDER
        WebElement genderRadio = driver.findElement(By.id("gender-radio-2"));
        WebElement genderLabel = driver.findElement(By.xpath("//*[@for='gender-radio-2']"));
        Assertions.assertEquals("Female", genderLabel.getText(),
                "Expected gender name is 'Female' but actual is " + genderLabel.getText());

        genderLabel.click();
        Assertions.assertTrue(genderRadio.isSelected(),
                "Expected radio-button 'Female' is not selected");

        //        NUMBER
        WebElement number = driver.findElement(By.id("userNumber"));
        Assertions.assertEquals("Mobile Number", number.getAttribute("placeholder"),
                "Expected mobile placeholder is 'Mobile Number' but actual is " + number.getAttribute("placeholder"));

        number.sendKeys("0123456789");
        Assertions.assertEquals("0123456789", number.getAttribute("value"),
                "Expected mobile number is '0123456789' but actual is " + number.getAttribute("value"));

        //        DATE OF BIRTH
        WebElement dateOfBirth = driver.findElement(By.id("dateOfBirth"));
        WebElement dateOfBirthInput = dateOfBirth.findElement(By.id("dateOfBirthInput"));
        String dateOfBirthString = dateOfBirthInput.getAttribute("value");

        DateTimeFormatter dateToFormat = new DateTimeFormatterBuilder().appendPattern("dd MMM yyyy").toFormatter(Locale.ENGLISH);
        LocalDate dateOfBirthParse = LocalDate.parse(dateOfBirthString, dateToFormat);
        LocalDate currentDate = LocalDate.now();

        Assertions.assertEquals(currentDate, dateOfBirthParse,
                "Error: mismatch between current date and date in the input");

        List<WebElement> datepicker = driver.findElements(By.className("react-datepicker"));
        Assertions.assertTrue(datepicker.isEmpty(),
                "Error: datepicker widget is prematurely active");

        dateOfBirthInput.click();

            //        DATE OF BIRTH | DATEPICKER | YEAR
        WebElement yearDropdown = driver.findElement(By.className("react-datepicker__year-select"));
        Select selectYear = new Select(yearDropdown);
        selectYear.selectByVisibleText("1977");
        Assertions.assertEquals("1977", yearDropdown.getAttribute("value"),
                "Expected year option is '1977' but actual is " + yearDropdown.getAttribute("value"));

            //        DATE OF BIRTH | DATEPICKER | MONTH
        WebElement monthDropdown = driver.findElement(By.className("react-datepicker__month-select"));
        Select selectMonth = new Select(monthDropdown);
        selectMonth.selectByVisibleText("May");
        Assertions.assertEquals("4", monthDropdown.getAttribute("value"),
                "Expected month option is '4' but actual is " + monthDropdown.getAttribute("value"));

            //        DATE OF BIRTH | DATEPICKER | DAY
        WebElement dayDatepicker = driver.findElement(By.xpath("//*[contains(@class,'react-datepicker__day') and text()='25']"));
        dayDatepicker.click();

        Assertions.assertEquals("25 May 1977", dateOfBirthInput.getAttribute("value"),
                "Expected date of birth is '25 May 1977' but actual is " + dateOfBirthInput.getAttribute("value"));

        //        SUBJECTS
        WebElement subjectsAutoCompleteInput = driver.findElement(By.id("subjectsInput"));
        subjectsAutoCompleteInput.sendKeys("En");
        Assertions.assertEquals("En", subjectsAutoCompleteInput.getAttribute("value"),
                "Expected auto-complete text is 'En' but actual is " + subjectsAutoCompleteInput.getAttribute("value"));

        WebElement autoCompleteMenu = driver.findElement(By.className("subjects-auto-complete__menu-list"));
        List<WebElement> autoCompleteOption = autoCompleteMenu.findElements(By.className("subjects-auto-complete__option"));
        List<String> expectedAutoCompleteOption = List.of("English", "Computer Science");
        for (int j = 0; j < autoCompleteOption.size(); ++j) {
            Assertions.assertEquals(expectedAutoCompleteOption.get(j), autoCompleteOption.get(j).getText(),
                    "Expected auto-complete option is '" + expectedAutoCompleteOption.get(j) + "' but actual is " + autoCompleteOption.get(j).getText());
        }
        WebElement autoCompleteOptionZero = driver.findElement(By.id("react-select-2-option-0"));
        autoCompleteOptionZero.click();

        subjectsAutoCompleteInput.sendKeys("Chemistry");
        Assertions.assertEquals("Chemistry", subjectsAutoCompleteInput.getAttribute("value"),
                "Expected auto-complete text is 'Chemistry' but actual is " + subjectsAutoCompleteInput.getAttribute("value"));

        WebElement autoCompleteOptionZeroDot = driver.findElement(By.className("subjects-auto-complete__menu"));
        Assertions.assertEquals("Chemistry", autoCompleteOptionZeroDot.getText(),
                "Expected auto-complete option is 'Chemistry' but actual is " + autoCompleteOptionZeroDot.getText());
        autoCompleteOptionZeroDot.click();

        List<WebElement> subjectsAutoCompleteDot = driver.findElements(By.className("subjects-auto-complete__multi-value"));
        List<String> expectedSubjectsAutoCompleteDot = List.of("English", "Chemistry");
        for (int k = 0; k < subjectsAutoCompleteDot.size(); ++k) {
            Assertions.assertEquals(expectedSubjectsAutoCompleteDot.get(k), subjectsAutoCompleteDot.get(k).getText(),
                    "Expected selected subject is '" + expectedSubjectsAutoCompleteDot.get(k) + "' but actual is " + subjectsAutoCompleteDot.get(k).getText());
        }

        //        HOBBIES
        WebElement hobbieCheckboxTwo = driver.findElement(By.id("hobbies-checkbox-2"));
        WebElement hobbieLabelTwo = driver.findElement(By.xpath("//*[@for='hobbies-checkbox-2']"));

        WebElement hobbieCheckboxThree = driver.findElement(By.id("hobbies-checkbox-3"));
        WebElement hobbieLabelThree = driver.findElement(By.xpath("//*[@for='hobbies-checkbox-3']"));

        Assertions.assertEquals("Reading", hobbieLabelTwo.getText(),
                "Expected hobbie text is 'Reading' but actual is " + hobbieLabelTwo.getText());
        hobbieLabelTwo.click();

        Assertions.assertEquals("Music", hobbieLabelThree.getText(),
                "Expected hobbie text is 'Music' but actual is " + hobbieLabelThree.getText());
        hobbieLabelThree.click();

        Assertions.assertTrue(hobbieCheckboxTwo.isSelected(), "Expected checkbox 'Reading' is not selected");
        Assertions.assertTrue(hobbieCheckboxThree.isSelected(), "Expected checkbox 'Music' is not selected");

        //        PICTURE
        String systemProperty = System.getProperty("user.dir");
        String uploadImage = systemProperty + "/src/test/java/academit/fifthwheel/images/scroofy.jpg";

        WebElement btnUploadPicture = driver.findElement(By.id("uploadPicture"));
        btnUploadPicture.sendKeys(uploadImage);

        Assertions.assertTrue(btnUploadPicture.getAttribute("value").contains("scroofy.jpg"),
                "Expected downloaded file name contains 'scroofy.jpg' but actual is not");

        //        CURRENT ADDRESS
        WebElement currentAddress = driver.findElement(By.id("currentAddress"));
        Assertions.assertEquals("Current Address", currentAddress.getAttribute("placeholder"),
                "Expected current address placeholder is Current Address but actual is " + currentAddress.getAttribute("placeholder"));

        currentAddress.sendKeys("11 Main St, Haines, AK 99827," + "\n" + "United States");
        Assertions.assertEquals("11 Main St, Haines, AK 99827," + "\n" + "United States", currentAddress.getAttribute("value"),
                "Expected address text is '" + "11 Main St, Haines, AK 99827," + "\n" + "United States" + "' but actual is " + currentAddress.getAttribute("value"));

        //        STATE
        WebElement state = driver.findElement(By.id("state"));
        Assertions.assertEquals("Select State", state.getText(),
                "Expected state placeholder is 'Select State' but actual is " + state.getText());
        state.click();

        WebElement stateDropdown = state.findElement(By.className("css-11unzgr"));
        WebElement stateOption = stateDropdown.findElement(By.id("react-select-3-option-2"));
        Assertions.assertEquals("Haryana", stateOption.getText(),
                "Expected dropdown state option is 'Haryana' but actual is " + stateOption.getText());
        stateOption.click();

        WebElement stateSingle = state.findElement(By.className("css-1uccc91-singleValue"));
        Assertions.assertEquals("Haryana", stateSingle.getText(),
                "Expected select state option is 'Haryana' but actual is " + stateSingle.getText());

        //        CITY
        WebElement city = driver.findElement(By.id("city"));
        Assertions.assertEquals("Select City", city.getText(),
                "Expected city placeholder is 'Select City' but actual is " + city.getText());
        wait.until(ExpectedConditions.elementToBeClickable(By.id("city")));
        city.click();

        WebElement cityDropdown = city.findElement(By.className("css-11unzgr"));
        WebElement cityOption = cityDropdown.findElement(By.id("react-select-4-option-1"));
        Assertions.assertEquals("Panipat", cityOption.getText(),
                "Expected dropdown city option is 'Panipat' but actual is " + cityOption.getText());
        cityOption.click();

        WebElement citySingle = city.findElement(By.className("css-1uccc91-singleValue"));
        Assertions.assertEquals("Panipat", citySingle.getText(),
                "Expected select city option is 'Panipat' but actual is " + citySingle.getText());

        //        button
        Thread.sleep(500);
        driver.findElement(By.id("submit")).click();

        //        summary data
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("table-responsive")));
        List<WebElement> tableLabelsValues = driver.findElements(By.xpath("//tbody//td[2]"));
        List<String> expectedTableLabelsValues = List.of("first name last name", "user322@gmail.com", "Female", "0123456789", "25 May,1977", "English, Chemistry", "Reading, Music", "scroofy.jpg", "11 Main St, Haines, AK 99827, United States", "Haryana Panipat");
        for (int m = 0; m < tableLabelsValues.size(); ++m) {
            Assertions.assertEquals(expectedTableLabelsValues.get(m), tableLabelsValues.get(m).getText(),
                    "Expected table text is " + expectedTableLabelsValues.get(m) + " but actual is " + tableLabelsValues.get(m).getText());
        }
    }
}
