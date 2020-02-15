package pages;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static java.time.Duration.ofSeconds;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * ������� �����
 * ����� ������������� �������������� ������ �� ������ � �������
 * ������ ����� ��� ���� �������
 */
public class BasePage {
    /**
     * ������ �� �������
     */
    public AndroidDriver driver;
    /**
     * ������� ������ �������� � �������
     */
    public final String XPATH_ANY_ELEM_WITH_TEXT = "//*[@text='%s']";

    /**
     * ������� ������ ��������, ����������� �����
     */
    public final String XPATH_ANY_ELEM_CONT_TEXT = "//*[contains(@text,'%s')]";

    /**
     * ������� ������ �������� � �������������� �������
     */
    public final String XPATH_ANY_ELEM_WITH_CONTENT_DESC = "//*[@content-desc='%s']";

    /**
     * ������� ������ ������������� ������������� ������
     */
    public final String XPATH_ALLOW_CAMERA = "//*[@text='���������' or @text='���������']";

    /**
     * ������� ����������� ������ ������� ����
     */
    public final String XPATH_CAMERA_BOTTOM_MENU = "//*[@content-desc='������']" +
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
        try {
            waitAbit(2000);
            if (isElementVisible(XPATH_ALLOW_CAMERA)) {
                getDriver().findElement(By.xpath(XPATH_ALLOW_CAMERA)).click();
                waitUntilNotVisible(XPATH_ALLOW_CAMERA);
            }
        } catch (Throwable e) {
            waitAbit(2000);
            if (isElementVisible(XPATH_ALLOW_CAMERA)) {
                getDriver().findElement(By.xpath(XPATH_ALLOW_CAMERA)).click();
                waitUntilNotVisible(XPATH_ALLOW_CAMERA);
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
        getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        boolean isVisible;
        try {
            isVisible = getDriver().findElement(By.xpath(locator)).isDisplayed();
        } catch (Throwable e) {
            isVisible = false;
        }
        getDriver().manage().timeouts().implicitlyWait(DriverManager.currentWait, TimeUnit.SECONDS);
        return isVisible;
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
            if (isElementVisible(String.format(XPATH_ANY_ELEM_WITH_TEXT, text)))
                break;
            else waitAbit(300);
        }
    }

    /**
     * ����� ������� ����������� ������ �������� � ��������� �������
     * @param text String �����
     */
    public void waitUntilAnyElementWithTextsIsVisible(String text) {
        String[] msgs;
        // ��������� �������
        StringBuilder buffer = new StringBuilder("//*[");
        // ���� � ������ ���� �������� [�], �� ��������� ������� ����� and
        if (text.contains("[�]")) {
            msgs = text.split("\\[�]");

            for (String msg1 : msgs) {
                buffer.append("@text='").append(msg1).append("' and ");
            }
            text = buffer.toString();
            text = text.substring(0, text.lastIndexOf(" and "));

            // ���� � ������ ���� �������� [���], �� ��������� ������� ����� or
            // ������ � ��� �������, ����� �� ����� �� �����, ����� ��������� ����� ������������
        } else if (text.contains("[���]")) {
            msgs = text.split("\\[���]");

            for (String msg1 : msgs) {
                buffer.append("@text='").append(msg1).append("' or ");
            }
            text = buffer.toString();
            text = text.substring(0, text.lastIndexOf(" or "));
        } else {
            buffer.append("@text='").append(text).append("'");
            text = buffer.toString();
        }

        //����������� ����������� �������
        text += "]";

        for (int i = 0; i < 60; i++) {
            try {
                if (getDriver().findElement(By.xpath(text)).isDisplayed())
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
            if (isElementVisible(String.format(XPATH_ANY_ELEM_WITH_TEXT, text)))
                waitAbit(300);
            else break;
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
     * ����� ������� �� ����� ������� � ��������� �������
     * @param text String �����
     */
    public void clickAnyElementWithTexts(String text) {
        String[] msgs;
        // ��������� �������
        StringBuilder buffer = new StringBuilder("//*[");
        // ���� � ������ ���� �������� [�], �� ��������� ������� ����� and
        if (text.contains("[�]")) {
            msgs = text.split("\\[�]");

            for (String msg1 : msgs) {
                buffer.append("@text='").append(msg1).append("' and ");
            }
            text = buffer.toString();
            text = text.substring(0, text.lastIndexOf(" and "));

            // ���� � ������ ���� �������� [���], �� ��������� ������� ����� or
            // ������ � ��� �������, ����� �� ����� �� �����, ����� ��������� ����� ������������
        } else if (text.contains("[���]")) {
            msgs = text.split("\\[���]");

            for (String msg1 : msgs) {
                buffer.append("@text='").append(msg1).append("' or ");
            }
            text = buffer.toString();
            text = text.substring(0, text.lastIndexOf(" or "));
        } else {
            buffer.append("@text='").append(text).append("'");
            text = buffer.toString();
        }

        //����������� ����������� �������
        text += "]";
        getDriver().findElement(By.xpath(text)).click();
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

    /**
     * ������������� ����� ��� �������� � ������
     *
     * @param fromPoint - ���������� ������ ������
     * @param toPoint   - ���������� ��������� ������
     */
    public void swipeByCoordinate(PointOption fromPoint, PointOption toPoint) {
        WebDriver driver = getDriver();
        TouchAction actions = new TouchAction((MobileDriver) driver);
        actions.press(fromPoint)
                .waitAction(waitOptions(ofSeconds(0)))
                .moveTo(toPoint)
                .release()
                .perform();
    }

    /**
     * �������� ����������
     */
    public void hideAndroidKeyboard() {
        for (int i = 0; i < 50; i++) {
            try {
                if (getDriver().isKeyboardShown())
                    getDriver().navigate().back();
                break;
            } catch (Throwable e) {
                assertThat(e.getMessage(), i < 49);
            }
        }
    }

    /**
     * �����-������� ��� ������ �������� �� ��������
     * @param xpath - xpath �� �������
     * @return - ������ ������� ��� ���
     */
    public boolean elementSearch(String xpath) {
        if (findElementWithSwipeDown(xpath, 30)) return true;
        else
            return findElementWithSwipeUp(xpath, 30);
    }

    /**
     * ����� ������ �������� �� ��������.
     * ����� ���� ������� �� ������, ���������� countBy ������� ����. �� ������ �������� ����������� ���������� ��������
     * @param xpath - ���� isID true, �� �������� � xpath id ��������
     * @param countBy - ���������� �������
     * @return - ������� ������ ��� ���
     */
    public boolean findElementWithSwipeDown(String xpath, int... countBy) {
        hideAndroidKeyboard();
        try {
            boolean isFoundElement;
            By myElement;
            myElement = By.xpath(xpath);

            isFoundElement = getDriver().findElements(myElement).size() > 0;
            int count = 0;
            String pageText = getXMLSource();

            if (countBy.length > 0) {
                while (!isFoundElement && count != countBy[0]) {
                    swipeByCoordinate(
                            PointOption.point(
                                    DriverManager.deviceWidth / 2,
                                    DriverManager.deviceHeight / 100 * 50
                            ),
                            PointOption.point(
                                    DriverManager.deviceWidth / 2,
                                    DriverManager.deviceHeight / 100 * 20)
                    );
                    isFoundElement = getDriver().findElements(myElement).size() > 0;
                    count++;

                    // ���� ��������� �������� ������ �� ����������, �� ����� ��������. ������ �� �������� �������
                    if (getXMLSource().equals(pageText)) break;
                    else pageText = getXMLSource();
                }
            } else {
                while (!isFoundElement && count != 10) {
                    swipeByCoordinate(
                            PointOption.point(
                                    DriverManager.deviceWidth / 2,
                                    DriverManager.deviceHeight / 100 * 50
                            ),
                            PointOption.point(
                                    DriverManager.deviceWidth / 2,
                                    DriverManager.deviceHeight / 100 * 20)
                    );
                    isFoundElement = getDriver().findElements(myElement).size() > 0;
                    count++;

                    // ���� ��������� �������� ������ �� ����������, �� ����� ��������. ������ �� �������� �������
                    if (getXMLSource().equals(pageText)) break;
                    else pageText = getXMLSource();
                }
            }
            return isFoundElement;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * ����� ������ �������� �� ��������.
     * ����� ���� ������� �� ������, ���������� countBy ������� �����. �� ������ �������� ����������� ���������� ��������
     * @param idORxpath - ������� �� ������� (id ��� xpath)
     * @param countBy - ���������� �������
     * @return - ������� ������ ��� ���
     */
    public boolean findElementWithSwipeUp(String idORxpath, int... countBy) {
        hideAndroidKeyboard();
        try {
            boolean isFoundElement;
            By myElement;
            myElement = By.xpath(idORxpath);

            isFoundElement = getDriver().findElements(myElement).size() > 0;
            int count = 0;
            String pageText = getXMLSource();

            if (countBy.length > 0) {
                while (!isFoundElement && count != countBy[0]) {
                    swipeByCoordinate(
                            PointOption.point(
                                    DriverManager.deviceWidth / 2,
                                    DriverManager.deviceHeight / 100 * 50
                            ),
                            PointOption.point(
                                    DriverManager.deviceWidth / 2,
                                    DriverManager.deviceHeight / 100 * 80)
                    );
                    isFoundElement = getDriver().findElements(myElement).size() > 0;
                    count++;

                    // ���� ��������� �������� ������ �� ����������, �� ����� ��������. ������ �� �������� �������
                    if (getXMLSource().equals(pageText)) break;
                    else pageText = getXMLSource();
                }
            } else {
                while (!isFoundElement && count != 10) {
                    swipeByCoordinate(
                            PointOption.point(
                                    DriverManager.deviceWidth / 2,
                                    DriverManager.deviceHeight / 100 * 50
                            ),
                            PointOption.point(
                                    DriverManager.deviceWidth / 2,
                                    DriverManager.deviceHeight / 100 * 80)
                    );
                    isFoundElement = getDriver().findElements(myElement).size() > 0;
                    count++;

                    // ���� ��������� �������� ������ �� ����������, �� ����� ��������. ������ �� �������� �������
                    if (getXMLSource().equals(pageText)) break;
                    else pageText = getXMLSource();
                }
            }
            return isFoundElement;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * �������� XML-��������� ��������. ������������ ��-�� ������� ���������� ����� ������ � ������.
     * ������� ����� �������� � ���������, �� � ������������ ��������� ������, ����� ���� �� �����
     * */
    public String getXMLSource() {
        String source = "";
        for (int i = 0; i < 50; i++) {
            try {
                source = getDriver().getPageSource();
                break;
            } catch (Throwable e) {
                assertThat(e.getMessage(), i < 49);
            }
        }
        return source;
    }
}
