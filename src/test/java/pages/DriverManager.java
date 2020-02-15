package pages;

import com.google.common.io.Files;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.appium.java_client.android.AndroidDriver;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.parser.JSONParser;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;

public class DriverManager {
    public static long currentWait;
    public static int deviceHeight;
    public static int deviceWidth;
    /**
     * Метод возвращает инстанс драйвера
     * @return AndroidDriver
     */
    public static AndroidDriver getDriver() {
        String udid = (System.getProperty("udid") != null) ? System.getProperty("udid") : "emulator-5554";
        invokeCmdCommand("adb -s " + udid + " shell pm clear com.instagram.android");

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName", "Android");
        cap.setCapability("udid", udid);
        cap.setCapability("deviceName", "device");
        cap.setCapability("platformVersion", "8.1");
        cap.setCapability("app", System.getProperty("user.dir").replaceAll("\\\\", "/") + "/src/test/resources/apk/instagram.apk");
        cap.setCapability("appPackage", "com.instagram.android");
        cap.setCapability("appWaitActivity", "com.instagram.nux.activity.SignedOutFragmentActivity");
        cap.setCapability("noReset", "true");
        cap.setCapability("fullReset", "false");
        cap.setCapability("newCommandTimeout", "30000");
        cap.setCapability("automationName", "UiAutomator2");
        AndroidDriver driver = null;
        try {
            System.out.println("starting new driver..." + LocalDateTime.now().toString());
            driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), cap);
            System.out.println("new driver is started!" + LocalDateTime.now().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
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
     * Получаем конфиг
     * @param prop String поле для поиска
     * @return String
     */
    public static String getConfig(String prop) {
        String str = "";
        try {
            str = String.join("", Files.readLines(
                    new File(System.getProperty("user.dir") + "/src/test/resources/Config.json"),
                    Charset.forName("windows-1251")
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type itemsType = new TypeToken<Map<String, Object>>() {
        }.getType();
        String configPath = str;
        Map<String, Object> map = new Gson().fromJson(configPath, itemsType);

        return map.get(prop).toString();
    }
}
