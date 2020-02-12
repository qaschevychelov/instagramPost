package pages;

import org.openqa.selenium.By;

public class AuthPage extends BasePage {
    private final String XPATH_LOGIN = "//*[@resource-id='']";
    private final String XPATH_PASS = "//*[@resource-id='']";
    private final String XPATH_SIGN_IN_BTN = "//*[@resource-id='']";

    /**
     * Метод задает логин
     * @param login String логин
     */
    public void setLogin(String login) {
        getDriver().findElement(By.xpath(XPATH_LOGIN)).sendKeys(login);
    }

    /**
     * Метод задает пароль
     * @param pass String пароль
     */
    public void setPass(String pass) {
        getDriver().findElement(By.xpath(XPATH_PASS)).sendKeys(pass);
    }

    /**
     * Метод кликает на кнопку входа
     */
    public void clickSignInBtn() {
        getDriver().findElement(By.xpath(XPATH_SIGN_IN_BTN)).click();
    }
}
