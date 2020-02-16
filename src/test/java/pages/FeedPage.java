package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.testng.Assert;

/**
 * ����� �����
 */
public class FeedPage extends BasePage {
    public FeedPage(AndroidDriver driver) {
        super.driver = driver;
    }

    /**
     * ������� ������ �������� � ����������� � �����
     */
    private static final String XPATH_POST_COMM = "//*[*[contains(@text,'%s')]]/preceding-sibling::*[1]" +
            "//*[@resource-id='com.instagram.android:id/row_feed_button_comment']";

    /**
     * ������� ���� ����� �����������
     */
    private static final String XPATH_COMM_FIELD = "//*[@resource-id='com.instagram.android:id/layout_comment_thread_edittext']";

    /**
     * ������ �������� � �������
     */
    private static final String XPATH_GO_TO_STORY = "//*[@resource-id='com.instagram.android:id/outer_container'][*[@text='���� �������']]";

    /**
     * ������� �������������� �������
     */
    private static final String XPATH_SEEN_STORY = "//*[@resource-id='com.instagram.android:id/seen_state']";

    /**
     * ����� ���� ����
     * @param desc String �������� � �����
     */
    public void findPost(String desc) {
        Assert.assertTrue(
                elementSearch(String.format(XPATH_ANY_ELEM_CONT_TEXT, desc)),
                "���� � ��������� '" + desc + "' �� ������"
        );
    }

    /**
     * ����� ��������� � ������������ �����
     * @param desc String �������� �����
     */
    public void goToComments(String desc) {
        getDriver().findElement(By.xpath(String.format(XPATH_POST_COMM, desc))).click();
    }

    /**
     * ����� ����� ����������� � �����
     * @param comment String �����������
     */
    public void setComment(String comment) {
        getDriver().findElement(By.xpath(XPATH_COMM_FIELD)).sendKeys(comment);
    }

    /**
     * ����� ��������� � ������ ��� �������
     */
    public void goToStoryCamera() {
        Assert.assertTrue(
                findElementWithSwipeUp(XPATH_GO_TO_STORY),
                "������ �������� � ������� �� �������"
        );
        getDriver().findElement(By.xpath(XPATH_GO_TO_STORY)).click();
    }

    /**
     * ����� ���������� ������ + ��� ���������� �������
     */
    public void waitUntilAddStoryBtnIsVisible() {
        waitUntilNotVisible(XPATH_SEEN_STORY);
    }
}
