package pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static io.appium.java_client.touch.offset.PointOption.point;

/**
 * Пейдж ленты
 */
public class FeedPage extends BasePage {
    public FeedPage(AndroidDriver driver) {
        super.driver = driver;
    }

    /**
     * локатор кнопки перехода в комментарии к посту
     */
    private static final String XPATH_POST_COMM = "//*[*[contains(@text,'%s')]]/preceding-sibling::*[1]" +
            "//*[@resource-id='com.instagram.android:id/row_feed_button_comment']";

    /**
     * локатор поля ввода комментария
     */
    private static final String XPATH_COMM_FIELD = "//*[@resource-id='com.instagram.android:id/layout_comment_thread_edittext']";

    /**
     * кнопка перехода в истории
     */
    private static final String XPATH_GO_TO_STORY = "//*[@resource-id='com.instagram.android:id/outer_container'][*[@text='Ваша история']]";

    /**
     * локатор опубликованной истории
     */
    private static final String XPATH_SEEN_STORY = "//*[@resource-id='com.instagram.android:id/seen_state']";

    /**
     * локатор целого поста на экране
     */
    private static final String XPATH_POST_IMG = "//*[@resource-id='com.instagram.android:id/zoomable_view_container']";

    /**
     * Описание к посту
     */
    private static final String XPATH_POST_DESC = "//*[@resource-id='com.instagram.android:id/row_feed_view_group_buttons']" +
            "/following-sibling::*[1]/*[@resource-id='com.instagram.android:id/row_feed_comment_textview_layout']";

    /**
     * комментарий к посту
     */
    private static final String XPATH_POST_COMMENT = "//*[@text='Добавьте комментарий...']/parent::*/parent::*/preceding-sibling::*[1]" +
            "/*[@resource-id='com.instagram.android:id/row_feed_comment_textview_layout']";

    /**
     * Метод ищет пост
     * @param desc String описание к посту
     */
    public void findPost(String desc) {
        Assert.assertTrue(
                elementSearch(String.format(XPATH_ANY_ELEM_CONT_TEXT, desc)),
                "Пост с описанием '" + desc + "' не найден"
        );
    }

    /**
     * Метод переходит к комментариям поста
     * @param desc String описание поста
     */
    public void goToComments(String desc) {
        getDriver().findElement(By.xpath(String.format(XPATH_POST_COMM, desc))).click();
    }

    /**
     * Метод пишет комментарий к посту
     * @param comment String комментарий
     */
    public void setComment(String comment) {
        getDriver().findElement(By.xpath(XPATH_COMM_FIELD)).sendKeys(comment);
    }

    /**
     * Метод переходит в камеру для историй
     */
    public void goToStoryCamera() {
        Assert.assertTrue(
                findElementWithSwipeUp(XPATH_GO_TO_STORY),
                "Кнопка перехода в истории не найдена"
        );
        getDriver().findElement(By.xpath(XPATH_GO_TO_STORY)).click();
    }

    /**
     * Метод дожидается кнопки + для добавления истории
     */
    public void waitUntilAddStoryBtnIsVisible() {
        waitUntilNotVisible(XPATH_SEEN_STORY);
    }

    /**
     * Метод выравнивает фото поста
     */
    public void normalizePostImg() {
        if (elementSearch(XPATH_POST_IMG)) {
            int height = 0;
            int counter = 0;
            String xml = "";
            while (getDriver().findElement(By.xpath(XPATH_POST_IMG)).getSize().getHeight() != height && counter != 10 && !getXMLSource().equals(xml)) {
                xml = getXMLSource();
                height = getDriver().findElement(By.xpath(XPATH_POST_IMG)).getSize().getHeight();
                swipeByCoordinate(
                        point(DriverManager.deviceWidth / 2, DriverManager.deviceHeight / 2),
                        point(DriverManager.deviceWidth / 2, DriverManager.deviceHeight / 100 * 40)
                );
                counter++;
            }
        }
    }

    /**
     * Метод сохраняет фото поста в папку
     * @param target String путь до выходного файла
     */
    public void savePostImg(String target) {
        // Делаем скрин всего экрана
        File screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        BufferedImage fullImg = null;
        try {
            fullImg = ImageIO.read(screenshot);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Получаем позицию элемента на экране
        Point point = getDriver().findElement(By.xpath(XPATH_POST_IMG)).getLocation();

        // Ширина и высота элемента
        int eleWidth = getDriver().findElement(By.xpath(XPATH_POST_IMG)).getSize().getWidth();
        int eleHeight = getDriver().findElement(By.xpath(XPATH_POST_IMG)).getSize().getHeight();

        // Обрезаем полный скрин на скрин элемента
        BufferedImage actBuf = fullImg.getSubimage(point.getX(), point.getY(),
                eleWidth, eleHeight);

        // пишем файл в папку
        try {
            ImageIO.write(actBuf, "png", screenshot);
            File screenshotLocation = new File(target);
            FileUtils.copyFile(screenshot, screenshotLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод ищет описание к посту
     */
    public String findPostDescription() {
        if (elementSearch(XPATH_POST_DESC)) {
            return getDriver().findElement(By.xpath(XPATH_POST_DESC)).getText();
        } else return "";
    }

    /**
     * Метод ищет комментарий к посту
     */
    public String findCommDescription() {
        if (elementSearch(XPATH_POST_COMMENT)) {
            return getDriver().findElement(By.xpath(XPATH_POST_COMMENT)).getText();
        } else return "";
    }

    /**
     * Метод свайпает на высоту поста
     */
    public void goToNextPost() {
        int loc = getDriver().findElement(By.xpath(XPATH_POST_IMG)).getLocation().getY();
        int height = getDriver().findElement(By.xpath(XPATH_POST_IMG)).getSize().getHeight();
        int times = (loc + height) / height;
        for (int i = 0; i < times; i++) {
            swipeByCoordinate(
                    point(DriverManager.deviceWidth / 2, DriverManager.deviceHeight / 100 * 80),
                    point(DriverManager.deviceWidth / 2, DriverManager.deviceHeight / 100 * 80 - height)
            );
        }
    }
}
