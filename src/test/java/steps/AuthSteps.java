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
        authPage.setLogin(login);
        authPage.setPass(pass);
        authPage.clickSignInBtn();
    }
}
