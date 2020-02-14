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
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;

public class BasePage {
    public AndroidDriver driver;
    private final String XPATH_ANY_ELEM_WITH_TEXT = "//*[@text='%s']";
    private final String XPATH_ANY_ELEM_WITH_CONTENT_DESC = "//*[@content-desc='%s']";
    private final String XPATH_ALLOW_CAMERA = "//*[@text='РАЗРЕШИТЬ' or @text='Разрешить']";

    // нижнее меню
    private final String XPATH_CAMERA_BOTTOM_MENU = "//*[@content-desc='Камера']" +
            "//*[@resource-id='com.instagram.android:id/tab_icon']";

    /**
     * Возвращает объект драйвера
     * @return AndroidDriver
     */
    public AndroidDriver getDriver() {
        return driver;
    }

    /**
     * Метод запускает закрытое приложение, установленное в капабилити текущего веб драйвера
     */
    public void activateApp() {
        String appPackage = getDriver().getCapabilities().asMap().get("appPackage").toString();
        getDriver().activateApp(appPackage);
    }

    /**
     * При первом запуске приложения, когда тест обращается к камере, система требует разрешения.
     * Метод разрешает доступ к камере устройства, если это необходимо.
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
     * Метод дожидается видимости элемента
     * @param locator String локатор элемента
     */
    public void waitUntilVisible(String locator) {
        for (int i = 0; i < 60; i++) {
            if (isElementVisible(locator))
                break;
            else waitAbit(300);
        }
    }

    /**
     * Метод возвращает видимость элемента
     * @param locator String локатор элемента
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
     * Метод дожидается исчезновения элемента
     * @param locator String локатор элемента
     */
    public void waitUntilNotVisible(String locator) {
        for (int i = 0; i < 60; i++) {
            if (isElementVisible(locator))
                waitAbit(300);
            else break;
        }
    }

    /**
     * Метод ждет
     * @param mills long количество миллисекунд
     */
    public void waitAbit(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Метод ожидает отображения любого элемента с текстом
     * @param text String текст
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
     * Метод ожидает отображения любого элемента с составным текстом
     * @param text String текст
     */
    public void waitUntilAnyElementWithTextsIsVisible(String text) {
        String[] msgs;
        // формируем локатор
        StringBuilder buffer = new StringBuilder("//*[");
        // если в тексте есть оператор [И], то формируем локатор через and
        if (text.contains("[И]")) {
            msgs = text.split("\\[И]");

            for (String msg1 : msgs) {
                buffer.append("@text='").append(msg1).append("' and ");
            }
            text = buffer.toString();
            text = text.substring(0, text.lastIndexOf(" and "));

            // если в тексте есть оператор [ИЛИ], то формируем локатор через or
            // удобно в тех случаях, когда мы точно не знаем, какое сообщение будет отображаться
        } else if (text.contains("[ИЛИ]")) {
            msgs = text.split("\\[ИЛИ]");

            for (String msg1 : msgs) {
                buffer.append("@text='").append(msg1).append("' or ");
            }
            text = buffer.toString();
            text = text.substring(0, text.lastIndexOf(" or "));
        } else {
            buffer.append("@text='").append(text).append("'");
            text = buffer.toString();
        }

        //заканчиваем формировать локатор
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
     * Метод ожидает исчезновения любого элемента с текстом
     * @param text String текст
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
     * Метод кликает на любой элемент с текстом
     * @param text String текст
     */
    public void clickAnyElementWithText(String text) {
        getDriver().findElement(By.xpath(String.format(XPATH_ANY_ELEM_WITH_TEXT, text))).click();
    }

    /**
     * Метод кликает на любой элемент с составным текстом
     * @param text String текст
     */
    public void clickAnyElementWithTexts(String text) {
        String[] msgs;
        // формируем локатор
        StringBuilder buffer = new StringBuilder("//*[");
        // если в тексте есть оператор [И], то формируем локатор через and
        if (text.contains("[И]")) {
            msgs = text.split("\\[И]");

            for (String msg1 : msgs) {
                buffer.append("@text='").append(msg1).append("' and ");
            }
            text = buffer.toString();
            text = text.substring(0, text.lastIndexOf(" and "));

            // если в тексте есть оператор [ИЛИ], то формируем локатор через or
            // удобно в тех случаях, когда мы точно не знаем, какое сообщение будет отображаться
        } else if (text.contains("[ИЛИ]")) {
            msgs = text.split("\\[ИЛИ]");

            for (String msg1 : msgs) {
                buffer.append("@text='").append(msg1).append("' or ");
            }
            text = buffer.toString();
            text = text.substring(0, text.lastIndexOf(" or "));
        } else {
            buffer.append("@text='").append(text).append("'");
            text = buffer.toString();
        }

        //заканчиваем формировать локатор
        text += "]";
        getDriver().findElement(By.xpath(text)).click();
    }

    /**
     * Метод ожидает отображения любого элемента с альтернативным текстом
     * @param text String текст
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
     * Метод ожидает исчезновения любого элемента с альтернативным текстом
     * @param text String текст
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
     * Метод кликает на любой элемент с альтернативным текстом
     * @param text String текст
     */
    public void clickAnyElementWithContDesc(String text) {
        getDriver().findElement(By.xpath(String.format(XPATH_ANY_ELEM_WITH_CONTENT_DESC, text))).click();
    }

    /**
     * Метод кликает по кнопке + (камера внизу экрана)
     */
    public void clickBottomCamera() {
        getDriver().findElement(By.xpath(XPATH_CAMERA_BOTTOM_MENU)).click();
    }

    /**
     * Метод пушит фото в галерею
     * @param path String путь к фото внутри телефона
     * @param file File сам файл
     */
    public void pushPhotosToGallery(String path, File file) {
        try {
            getDriver().pushFile(path, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
