package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;

public class BasePage {
    public AndroidDriver driver;

    private final String XPATH_ANY_ELEM_WITH_TEXT = "//*[@text='%s']";
    private final String XPATH_ANY_ELEM_WITH_CONTENT_DESC = "//*[@content-desc='%s']";
    private final String XPATH_ALLOW_CAMERA = "//*[@text='���������' or @text='���������']";

    // ������ ����
    private final String XPATH_CAMERA_BOTTOM_MENU = "//*[@content-desc='������']" +
            "//*[@resource-id='com.instagram.android:id/tab_icon']";

    /**
     * ���������� ������ ��������
     * @return AndroidDriver
     */
    public AndroidDriver getDriver() {
        return driver;
    }

    /**
     * ����� ��������� �������� ����������, ������������� � ���������� �������� ��� ��������
     */
    public void activateApp() {
        String appPackage = getDriver().getCapabilities().asMap().get("appPackage").toString();
        getDriver().activateApp(appPackage);
    }

    /**
     * ��� ������ ������� ����������, ����� ���� ���������� � ������, ������� ������� ����������.
     * ����� ��������� ������ � ������ ����������, ���� ��� ����������.
     */
    public void allowCameraRecording() {
        int count = 0;
        while (count < 3) {
            count++;
            try {
                waitAbit(2000);
                if (isElementVisible(XPATH_ALLOW_CAMERA)) {
                    getDriver().findElement(By.xpath(XPATH_ALLOW_CAMERA)).click();
                    waitUntilNotVisible(XPATH_ALLOW_CAMERA);
                    break;
                }
            } catch (Throwable e) {
                waitAbit(2000);
                if (isElementVisible(XPATH_ALLOW_CAMERA)) {
                    getDriver().findElement(By.xpath(XPATH_ALLOW_CAMERA)).click();
                    waitUntilNotVisible(XPATH_ALLOW_CAMERA);
                    break;
                }
            }
        }
    }

    /**
     * ����� ���������� ��������� ��������
     * @param locator String ������� ��������
     */
    public void waitUntilVisible(String locator) {
        for (int i = 0; i < 60; i++) {
            if (isElementVisible(locator))
                break;
            else waitAbit(300);
        }
    }

    /**
     * ����� ���������� ��������� ��������
     * @param locator String ������� ��������
     * @return boolean
     */
    public boolean isElementVisible(String locator) {
        try {
            return getDriver().findElement(By.xpath(locator)).isDisplayed();
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * ����� ���������� ������������ ��������
     * @param locator String ������� ��������
     */
    public void waitUntilNotVisible(String locator) {
        for (int i = 0; i < 60; i++) {
            if (isElementVisible(locator))
                waitAbit(300);
            else break;
        }
    }

    /**
     * ����� ����
     * @param mills long ���������� �����������
     */
    public void waitAbit(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * ����� ������� ����������� ������ �������� � �������
     * @param text String �����
     */
    public void waitUntilAnyElementWithTextIsVisible(String text) {
        for (int i = 0; i < 60; i++) {
            try {
                if (getDriver().findElement(By.xpath(String.format(XPATH_ANY_ELEM_WITH_TEXT, text))).isDisplayed())
                    break;
            } catch (Throwable e) {
                waitAbit(300);
            }
        }
    }

    /**
     * ����� ������� ������������ ������ �������� � �������
     * @param text String �����
     */
    public void waitUntilAnyElementWithTextIsNotVisible(String text) {
        for (int i = 0; i < 60; i++) {
            try {
                if (getDriver().findElement(By.xpath(String.format(XPATH_ANY_ELEM_WITH_TEXT, text))).isDisplayed())
                    waitAbit(300);
            } catch (Throwable e) {
                break;
            }
        }
    }

    /**
     * ����� ������� �� ����� ������� � �������
     * @param text String �����
     */
    public void clickAnyElementWithText(String text) {
        getDriver().findElement(By.xpath(String.format(XPATH_ANY_ELEM_WITH_TEXT, text))).click();
    }

    /**
     * ����� ������� ����������� ������ �������� � �������������� �������
     * @param text String �����
     */
    public void waitUntilAnyElementWithContDescIsVisible(String text) {
        for (int i = 0; i < 60; i++) {
            try {
                if (getDriver().findElement(By.xpath(String.format(XPATH_ANY_ELEM_WITH_CONTENT_DESC, text))).isDisplayed())
                    break;
            } catch (Throwable e) {
                waitAbit(300);
            }
        }
    }

    /**
     * ����� ������� ������������ ������ �������� � �������������� �������
     * @param text String �����
     */
    public void waitUntilAnyElementWithContDescIsNotVisible(String text) {
        for (int i = 0; i < 60; i++) {
            try {
                if (getDriver().findElement(By.xpath(String.format(XPATH_ANY_ELEM_WITH_CONTENT_DESC, text))).isDisplayed())
                    waitAbit(300);
            } catch (Throwable e) {
                break;
            }
        }
    }

    /**
     * ����� ������� �� ����� ������� � �������������� �������
     * @param text String �����
     */
    public void clickAnyElementWithContDesc(String text) {
        getDriver().findElement(By.xpath(String.format(XPATH_ANY_ELEM_WITH_CONTENT_DESC, text))).click();
    }

    /**
     * ����� ������� �� ������ + (������ ����� ������)
     */
    public void clickBottomCamera() {
        getDriver().findElement(By.xpath(XPATH_CAMERA_BOTTOM_MENU)).click();
    }

    /**
     * ����� ����� ���� � �������
     * @param path String ���� � ���� ������ ��������
     * @param file File ��� ����
     */
    public void pushPhotosToGallery(String path, File file) {
        try {
            getDriver().pushFile(path, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
