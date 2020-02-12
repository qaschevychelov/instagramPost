package pages;

import org.openqa.selenium.By;

public class AuthPage extends BasePage {
    private final String XPATH_LOGIN = "//*[@resource-id='']";
    private final String XPATH_PASS = "//*[@resource-id='']";
    private final String XPATH_SIGN_IN_BTN = "//*[@resource-id='']";

    /**
     * Метод задает поле Логин
     * @param login String значение
     */
    public void setLogin(String login) {
        getDriver().findElement(By.xpath(XPATH_LOGIN)).sendKeys(login);
    }

    /**
     * Метод задает поле Пароль
     * @param pass String значение
     */
    public void setPass(String pass) {
        getDriver().findElement(By.xpath(XPATH_PASS)).sendKeys(pass);
    }

    /**
     * Метод нажимает на кнопку входа
     */
    public void clickSignInBtn() {
        getDriver().findElement(By.xpath(XPATH_SIGN_IN_BTN)).click();
    }
}
