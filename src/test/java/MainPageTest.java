import client.Constant;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.MainPage;

import static org.junit.Assert.assertTrue;

public class MainPageTest {
    WebDriver driver;
    MainPage mainPage;

    @Before
    public void setUp() {
        driver = WebDriverFactory.getWebDriver();
        mainPage = new MainPage(driver);
        driver.get(Constant.MAIN_PAGE_URL);
        driver.manage().window().maximize();
    }

    @Test
    @DisplayName("Проверка переключения вкладки Булки")
    public void checkBunsSwitchIsDisplayedTest() {
        mainPage.clickSaucesSwitch();
        mainPage.clickBunsSwitch();
        assertTrue(mainPage.bunsHeaderIsDisplayed());
    }

    @Test
    @DisplayName("Проверка переключения вкладки Соусы")
    public void checkSaucesSwitchIsDisplayedTest() {
        mainPage.clickSaucesSwitch();
        assertTrue(mainPage.saucesHeaderIsDisplayed());
    }

    @Test
    @DisplayName("Проверка переключения вкладки Начинки")
    public void checkFillingsSwitchIsDisplayedTest() {
        mainPage.clickFillingsSwitch();
        assertTrue(mainPage.fillingsHeaderIsDisplayed());
    }

    @After
    public void teardown() {
        driver.quit();
    }
}
