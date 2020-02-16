package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * ����� ��������
 */
public class SettingsPage extends BasePage {
    public SettingsPage(AndroidDriver driver) {
        super.driver = driver;
    }

    /**
     * �������������
     */
    private static final String XPATH_SWITCH = "//*[@resource-id='com.instagram.android:id/row_menu_item_switch']";

    /**
     * ����� ��������� ���������� ������������ ����������
     */
    public void turnOffSavings() {
        List<WebElement> list = getDriver().findElements(By.xpath(XPATH_SWITCH));
        list.forEach(one -> {
            if (Boolean.parseBoolean(one.getAttribute("checked")))
                one.click();
        });
    }
}
