import io.appium.java_client.android.AndroidDriver;
import org.slf4j.MDC;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.junit.Assert.fail;

public class InstagramPost extends BasePage {
    AndroidDriver driver;

    @BeforeClass
    public void before() {
        driver = getDriver();
    }

    @Test(enabled = true, description = "Авторизация в instagram")
    public void auth_01() {
        try {
            MDC.put("instagramPostAuth", "Авторизация");

        } catch (Throwable e) {
            fail(e.getMessage());
        }
    }
}
