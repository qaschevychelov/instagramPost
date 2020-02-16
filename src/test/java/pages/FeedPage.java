package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.testng.Assert;

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
}
