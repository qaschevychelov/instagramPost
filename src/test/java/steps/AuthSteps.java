package steps;

import pages.AuthPage;

public class AuthSteps {
    private AuthPage authPage = new AuthPage();

    /**
     * Метод авторизует пользователя
     * @param login String логин
     * @param pass String пароль
     */
    public void auth(String login, String pass) {
        authPage.waitUntilAnyElementWithTextIsVisible("Войти");
        authPage.clickAnyElementWithText("Войти");
        authPage.waitUntilAnyElementWithTextIsVisible("Вход");
        authPage.setLogin(login);
        authPage.setPass(pass);
        authPage.clickAnyElementWithText("Вход");
        authPage.waitUntilAnyElementWithTextIsNotVisible("Вход");
        authPage.waitUntilAnyElementWithContDescIsVisible("Дом");
    }
}
