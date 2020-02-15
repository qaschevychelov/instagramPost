package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * ����� ���������� ������ �����
 */
public class NewPostPage extends BasePage {
    public NewPostPage(AndroidDriver driver) {
        super.driver = driver;
    }
    private static final String XPATH_DESC = "//*[@resource-id='com.instagram.android:id/caption_text_view']";

    /**
     * ����� ������ �������� � �����
     * @param desc String ��������
     */
    public void setDescription(String desc) {
        getDriver().findElement(By.xpath(XPATH_DESC)).sendKeys(desc);
    }
}
