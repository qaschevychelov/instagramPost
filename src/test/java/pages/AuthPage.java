package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class AuthPage extends BasePage {
    public AuthPage(AndroidDriver driver) {
        super.driver = driver;
    }
    private final String XPATH_LOGIN = "//*[@resource-id='com.instagram.android:id/login_username']";
    private final String XPATH_PASS = "//*[@resource-id='com.instagram.android:id/password']";
    private final String XPATH_SIGN_IN_BTN = "//*[@resource-id='']";

    /**
     * ����� ������ �����
     * @param login String �����
     */
    public void setLogin(String login) {
        getDriver().findElement(By.xpath(XPATH_LOGIN)).sendKeys(login);
    }

    /**
     * ����� ������ ������
     * @param pass String ������
     */
    public void setPass(String pass) {
        getDriver().findElement(By.xpath(XPATH_PASS)).sendKeys(pass);
    }

    /**
     * ����� ������� �� ������ �����
     */
    public void clickSignInBtn() {
        getDriver().findElement(By.xpath(XPATH_SIGN_IN_BTN)).click();
    }
}
