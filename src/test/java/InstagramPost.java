import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.BasePage;
import steps.AuthSteps;

import static org.junit.Assert.fail;

/**
 * Автотест для Instagram
 */
public class InstagramPost extends BasePage {
    private AuthSteps authSteps;

    // тестовые данные
    private String login = "";
    private String pass = "";

    @BeforeClass
    public void before() {
        authSteps = new AuthSteps();
    }

    @Test(enabled = true, description = "Авторизация в instagram")
    public void step_01() {
        try {
            authSteps.auth(login, pass);
        } catch (Throwable e) {
            fail(e.getMessage());
        }
    }
}
