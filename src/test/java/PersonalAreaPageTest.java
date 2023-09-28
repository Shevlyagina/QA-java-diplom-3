import client.Constant;
import client.UserClient;
import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.AuthorizationPage;
import pageobject.MainPage;
import pageobject.PersonalAreaPage;

import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.junit.Assert.assertEquals;

public class PersonalAreaPageTest {
    WebDriver driver;
    AuthorizationPage authorizationPage;
    MainPage mainPage;
    PersonalAreaPage personalAreaPage;
    String accessToken;
    UserClient userClient;
    String email;
    String password;
    String name;

    @Before
    public void setUp() {
        driver = WebDriverFactory.getWebDriver();
        driver.get(Constant.LOGIN_PAGE_URL);
        driver.manage().window().maximize();
        authorizationPage = new AuthorizationPage(driver);
        mainPage = new MainPage(driver);
        personalAreaPage = new PersonalAreaPage(driver);
        userClient = new UserClient();
        Faker faker = new Faker();
        email = faker.internet().emailAddress();
        password = faker.internet().password();
        name = faker.name().username();
        accessToken = userClient.createUser(email, password, name).extract().body().path("accessToken");
        authorizationPage.userLogin(email, password);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.urlToBe(Constant.MAIN_PAGE_URL));
    }

    @Test
    @DisplayName("Проверка кнопки Личный кабинет")
    public void checkPersonalAreaButtonTest() {
        mainPage.clickPersonalAreaButton();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.urlToBe(Constant.PERSONAL_AREA_PAGE_URL));
        assertEquals(Constant.PERSONAL_AREA_PAGE_URL, personalAreaPage.getLink());
    }

    @Test
    @DisplayName("Проверка выхода из аккаунта")
    public void checkLogOutTest() {
        mainPage.clickPersonalAreaButton();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.urlToBe(Constant.PERSONAL_AREA_PAGE_URL));
        personalAreaPage.clickExitButton();
        wait.until(ExpectedConditions.urlToBe(Constant.LOGIN_PAGE_URL));
        assertEquals(Constant.LOGIN_PAGE_URL, authorizationPage.getLink());
    }

    @Test
    @DisplayName("Проверка перехода в конструктор по клику на логотип")
    public void checkLogoTest() {
        mainPage.clickPersonalAreaButton();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.urlToBe(Constant.PERSONAL_AREA_PAGE_URL));
        personalAreaPage.clickLogo();
        assertEquals(Constant.MAIN_PAGE_URL, personalAreaPage.getLink());
    }

    @Test
    @DisplayName("Проверка перехода в конструктор по клику Конструктор")
    public void checkConstructorButtonTest() {
        mainPage.clickPersonalAreaButton();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.urlToBe(Constant.PERSONAL_AREA_PAGE_URL));
        personalAreaPage.clickConstructorButton();
        assertEquals(Constant.MAIN_PAGE_URL, personalAreaPage.getLink());
    }

    @After
    public void teardown() {
        driver.quit();
        if (accessToken != null) {
            userClient.deleteUser(accessToken)
                    .assertThat().statusCode(SC_ACCEPTED);
        }
    }
}