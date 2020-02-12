import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.BasePage;
import steps.AuthSteps;

import static org.junit.Assert.fail;

/**
 * �������� ��� Instagram
 */
public class InstagramPost extends BasePage {
    private AuthSteps authSteps;

    // �������� ������
    private String login = "";
    private String pass = "";

    @BeforeClass
    public void before() {
        authSteps = new AuthSteps();
    }

    @Test(enabled = true, description = "����������� � instagram")
    public void step_01() {
        try {
            authSteps.auth(login, pass);
        } catch (Throwable e) {
            fail(e.getMessage());
        }
    }
}
