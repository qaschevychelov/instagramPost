package steps;

import pages.AuthPage;

public class AuthSteps {
    private AuthPage authPage = new AuthPage();

    /**
     * ����� ���������� ������������
     * @param login String �����
     * @param pass String ������
     */
    public void auth(String login, String pass) {
        authPage.waitUntilAnyElementWithTextIsVisible("�����");
        authPage.clickAnyElementWithText("�����");
        authPage.waitUntilAnyElementWithTextIsVisible("����");
        authPage.setLogin(login);
        authPage.setPass(pass);
        authPage.clickAnyElementWithText("����");
        authPage.waitUntilAnyElementWithTextIsNotVisible("����");
        authPage.waitUntilAnyElementWithContDescIsVisible("���");
    }
}
