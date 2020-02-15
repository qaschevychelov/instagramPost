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
 * Базовый пейдж
 * Здесь сосредоточены низкоуровневые методы по работе с экраном
 * Методы общие для всех экранов
 */
public class BasePage {
    /**
     * ссылка на драйвер
     */
    public AndroidDriver driver;
    /**
     * локатор любого элемента с текстом
     */
    public final String XPATH_ANY_ELEM_WITH_TEXT = "//*[@text='%s']";

    /**
     * локатор любого элемента, содержащего текст
     */
    public final String XPATH_ANY_ELEM_CONT_TEXT = "//*[contains(@text,'%s')]";

    /**
     * локатор любого элемента с альтернативным текстом
     */
    public final String XPATH_ANY_ELEM_WITH_CONTENT_DESC = "//*[@content-desc='%s']";

    /**
     * локатор кнопки подтверждения использования камеры
     */
    public final String XPATH_ALLOW_CAMERA = "//*[@text='РАЗРЕШИТЬ' or @text='Разрешить']";

    /**
     * локатор центральной кнопки нижнего меню
     */
    public final String XPATH_CAMERA_BOTTOM_MENU = "//*[@content-desc='Камера']" +
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
            if (isElementVisible(String.format(XPATH_ANY_ELEM_WITH_TEXT, text)))
                break;
            else waitAbit(300);
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
            if (isElementVisible(String.format(XPATH_ANY_ELEM_WITH_TEXT, text)))
                waitAbit(300);
            else break;
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

    /**
     * перегруженный метод без задержки в свайпе
     *
     * @param fromPoint - координаты начала свайпа
     * @param toPoint   - координаты окончания свайпа
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
     * Скрывает клавиатуру
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
     * Метод-обертка для поиска элемента на странице
     * @param xpath - xpath на элемент
     * @return - найден элемент или нет
     */
    public boolean elementSearch(String xpath) {
        if (findElementWithSwipeDown(xpath, 30)) return true;
        else
            return findElementWithSwipeUp(xpath, 30);
    }

    /**
     * Метод поиска элемента на странице.
     * Далее если элемент не найден, происходит countBy свайпов вниз. На каждой итерации проверяется нахождение элемента
     * @param xpath - если isID true, то передаем в xpath id элемента
     * @param countBy - количество свайпов
     * @return - элемент найден или нет
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

                    // если структура страницы совсем не изменилась, то можно выходить. Значит мы достигли границы
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

                    // если структура страницы совсем не изменилась, то можно выходить. Значит мы достигли границы
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
     * Метод поиска элемента на странице.
     * Далее если элемент не найден, происходит countBy свайпов вверх. На каждой итерации проверяется нахождение элемента
     * @param idORxpath - локатор на элемент (id или xpath)
     * @param countBy - количество свайпов
     * @return - элемент найден или нет
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

                    // если структура страницы совсем не изменилась, то можно выходить. Значит мы достигли границы
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

                    // если структура страницы совсем не изменилась, то можно выходить. Значит мы достигли границы
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
     * Получает XML-структуру страницы. Периодически из-за разрыва соединения может падать в ошибку.
     * Поэтому метод выполнен с рекурсией, НО с обязательным счетчиком выхода, чтобы тест не завис
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
