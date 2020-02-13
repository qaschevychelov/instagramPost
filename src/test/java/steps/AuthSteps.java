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
     * ����� ���������� ������������
     * @param login String �����
     * @param pass String ������
     */
    public void auth(String login, String pass) {
        authPage.waitUntilAnyElementWithTextsIsVisible("�����[���]��� ���� �������? �������.");
        authPage.clickAnyElementWithTexts("�����[���]��� ���� �������? �������.");
        authPage.waitUntilAnyElementWithTextIsVisible("����");
        authPage.setLogin(login);
        authPage.setPass(pass);
        authPage.clickAnyElementWithText("����");
        authPage.waitUntilAnyElementWithTextIsNotVisible("����");
        authPage.waitUntilAnyElementWithContDescIsVisible("���");
    }
}
