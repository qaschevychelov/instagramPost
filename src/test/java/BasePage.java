import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

public class BasePage {
    public AndroidDriver getDriver() {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName", "Android");
        cap.setCapability("udid", "emulator-5554");
        cap.setCapability("deviceName", "device");
        cap.setCapability("platformVersion", "8.1");
        // TODO: брать приложение из ресурсов
        cap.setCapability("app", "src\\test\\resources\\apk\\instagram.apk");
        cap.setCapability("appPackage", "com.instagram.android");
        cap.setCapability("appActivity", "com.instagram.nux.activity.SignedOutFragmentActivity");
        cap.setCapability("noReset", "true");
        cap.setCapability("fullReset", "false");
        cap.setCapability("newCommandTimeout", "30000");
        cap.setCapability("automationName", "Espresso");
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
