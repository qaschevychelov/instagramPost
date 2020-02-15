package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Пейдж галереи для выбора фото
 */
public class GalleryPage extends BasePage {
    public GalleryPage(AndroidDriver driver) {
        super.driver = driver;
    }
    private static final String XPATH_MINI_IMG = "//*[@content-desc='Миниатюра фото']";

    /**
     * Метод кликает по миниатюре фото
     * @param index int индекс
     */
    public void clickPhotoMini(int index) {
        ((WebElement)getDriver().findElements(By.xpath(XPATH_MINI_IMG)).get(index - 1)).click();
    }
}
