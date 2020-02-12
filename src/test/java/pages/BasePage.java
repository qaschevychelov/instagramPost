package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;

public class BasePage {
    private AndroidDriver driver;

    private final String XPATH_ANY_ELEM_WITH_TEXT = "//*[@text='%s']";
    private final String XPATH_ANY_ELEM_WITH_CONTENT_DESC = "//*[@content-desc='%s']";

    /**
     * Метод возвращает инстанс драйвера
     * @return AndroidDriver
     */
    public AndroidDriver getDriver() {
        if (this.driver != null) {
            return this.driver;
        }
        else {
            String udid = (System.getProperty("udid") != null) ? System.getProperty("udid") : "emulator-5554";
            invokeCmdCommand("adb -s " + udid + " shell pm clear com.instagram.android");

            DesiredCapabilities cap = new DesiredCapabilities();
            cap.setCapability("platformName", "Android");
            cap.setCapability("udid", udid);
            cap.setCapability("deviceName", "device");
            cap.setCapability("platformVersion", "8.1");
            cap.setCapability("app", System.getProperty("user.dir") + "/src/test/resources/apk/instagram.apk");
            cap.setCapability("appPackage", "com.instagram.android");
            cap.setCapability("appWaitActivity", "com.instagram.nux.activity.SignedOutFragmentActivity");
            cap.setCapability("noReset", "true");
            cap.setCapability("fullReset", "false");
            cap.setCapability("newCommandTimeout", "30000");
            cap.setCapability("automationName", "UiAutomator2");
            try {
                System.out.println("starting new driver..." + LocalDateTime.now().toString());
                this.driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), cap);
                System.out.println("new driver is started!" + LocalDateTime.now().toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return this.driver;
        }

    }

    /**
     * Метод запускает закрытое приложение, установленное в капабилити текущего веб драйвера
     */
    public void activateApp() {
        String appPackage = getDriver().getCapabilities().asMap().get("appPackage").toString();
        getDriver().activateApp(appPackage);
    }

    private static void invokeCmdCommand(String command) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            assertThat(e.getMessage(), false);
        }
        try (
                BufferedReader stdInput = new BufferedReader(new
                        InputStreamReader(Objects.requireNonNull(process).getInputStream()));
                BufferedReader stdError = new BufferedReader(new
                        InputStreamReader(process.getErrorStream()))
        ) {
            String msg;
            // считываем вывод команды
            while ((msg = stdInput.readLine()) != null) {
                System.out.println("Команда вернула следующее сообщение: " + msg);
            }
            // считываем ошибки, если есть
            while ((msg = stdError.readLine()) != null) {
                System.out.println("Результат команды: " + msg);
            }
        } catch (IOException e) {
            assertThat(e.getMessage(), false);
        }
    }

    /**
     * Метод дожидается видимости элемента
     * @param webElement WebElement элемент
     */
    public void waitUntilVisible(WebElement webElement) {
        for (int i = 0; i < 60; i++) {
            try {
                if (webElement.isDisplayed())
                    break;
            } catch (Throwable e) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Метод дожидается исчезновения элемента
     * @param webElement WebElement элемент
     */
    public void waitUntilNotVisible(WebElement webElement) {
        for (int i = 0; i < 60; i++) {
            try {
                if (webElement.isDisplayed())
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
            } catch (Throwable e) {
                break;
            }
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
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
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
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
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
     * Метод ожидает отображения любого элемента с альтернативным текстом
     * @param text String текст
     */
    public void waitUntilAnyElementWithContDescIsVisible(String text) {
        for (int i = 0; i < 60; i++) {
            try {
                if (getDriver().findElement(By.xpath(String.format(XPATH_ANY_ELEM_WITH_CONTENT_DESC, text))).isDisplayed())
                    break;
            } catch (Throwable e) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
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
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
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
}
