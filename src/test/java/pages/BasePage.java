package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

public class BasePage {
    private AndroidDriver driver;

    /**
     * Метод возвращает инстанс драйвера
     * @return AndroidDriver
     */
    public AndroidDriver getDriver() {
        if (this.driver != null)
            return this.driver;
        else {
            DesiredCapabilities cap = new DesiredCapabilities();
            cap.setCapability("platformName", "Android");
            cap.setCapability("udid", "emulator-5554");
            cap.setCapability("deviceName", "device");
            cap.setCapability("platformVersion", "8.1");
            cap.setCapability("app", System.getProperty("user.dir") + "/src/test/resources/apk/instagram.apk");
            cap.setCapability("appPackage", "com.instagram.android");
            cap.setCapability("appWaitActivity", "com.instagram.nux.activity.SignedOutFragmentActivity");
            cap.setCapability("noReset", "true");
            cap.setCapability("fullReset", "false");
            cap.setCapability("newCommandTimeout", "30000");
            cap.setCapability("automationName", "UiAutomator2");
            AndroidDriver androidDriver = null;
            try {
                System.out.println("starting new driver..." + LocalDateTime.now().toString());
                androidDriver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), cap);
                System.out.println("new driver is started!" + LocalDateTime.now().toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return androidDriver;
        }

    }

    /**
     * Метод дожидается видимости
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
     * Метод дожидается исчезновения
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
}
