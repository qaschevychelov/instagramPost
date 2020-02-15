package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * ����� ������� � ��������
 */
public class StoryGalleryPage extends BasePage {
    public StoryGalleryPage(AndroidDriver driver) {
        super.driver = driver;
    }

    /**
     * ������� ����
     */
    private static final String XPATH_PHOTO = "//*[@resource-id='com.instagram.android:id/gallery_grid_item_thumbnail']";

    /**
     * ������� ������ ���������� � �������
     */
    private static final String XPATH_SHARE = "//*[@resource-id='com.instagram.android:id/row_add_to_story_container']" +
            "//*[@text='����������']";

    /**
     * ������ �������� � �������
     */
    private static final String XPATH_GO_TO_GALLERY = "//*[@resource-id='com.instagram.android:id/gallery_preview_button']";

    /**
     * ����� ��������� �������
     */
    public void goToGallery() {
        getDriver().findElement(By.xpath(XPATH_GO_TO_GALLERY)).click();
    }

    /**
     * ����� ������� �� ����
     * @param index int ������ ����
     */
    public void selectPhoto(int index) {
        ((WebElement) getDriver().findElements(By.xpath(XPATH_PHOTO)).get(index - 1)).click();
    }

    /**
     * ����� �������� ���������� � �������
     */
    public void share() {
        getDriver().findElement(By.xpath(XPATH_SHARE)).click();
    }
}
