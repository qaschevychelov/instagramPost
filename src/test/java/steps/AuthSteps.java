package steps;

import io.appium.java_client.android.AndroidDriver;
import pages.AuthPage;

public class AuthSteps {
    AndroidDriver driver;
    AuthPage authPage;
    public AuthSteps(AndroidDriver driver) {
        this.driver = driver;
        authPage = new AuthPage(this.driver);
    }

    /**
     * Метод авторизует пользователя
     * @param login String логин
     * @param pass String пароль
     */
    public void auth(String login, String pass) {
        authPage.waitUntilAnyElementWithTextsIsVisible("Войти[ИЛИ]Уже есть аккаунт? Войдите.");
        authPage.clickAnyElementWithTexts("Войти[ИЛИ]Уже есть аккаунт? Войдите.");
        authPage.waitUntilAnyElementWithTextIsVisible("Вход");
        authPage.setLogin(login);
        authPage.setPass(pass);
        authPage.clickAnyElementWithText("Вход");
        authPage.waitUntilAnyElementWithTextIsNotVisible("Вход");
        authPage.waitUntilAnyElementWithContDescIsVisible("Дом");
    }
}
