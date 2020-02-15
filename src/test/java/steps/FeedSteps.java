package steps;

import io.appium.java_client.android.AndroidDriver;
import pages.FeedPage;
import pages.StoryGalleryPage;

/**
 * Логика ленты
 */
public class FeedSteps {
    private AndroidDriver driver;
    private FeedPage feedPage;
    private StoryGalleryPage storyGalleryPage;

    public FeedSteps(AndroidDriver driver) {
        this.driver = driver;
        feedPage = new FeedPage(this.driver);
        storyGalleryPage = new StoryGalleryPage(this.driver);
    }


    /**
     * Метод постит комментарий к посту
     * @param desc String описание поста
     * @param comment String сам комментарий
     */
    public void postComment(String desc, String comment) {
        feedPage.findPost(desc);
        feedPage.goToComments(desc);
        feedPage.waitUntilAnyElementWithTextIsVisible("Добавьте комментарий...");
        feedPage.hideAndroidKeyboard();
        feedPage.setComment(comment);
        feedPage.clickAnyElementWithText("Опубликовать");
        feedPage.waitUntilAnyElementWithTextIsNotVisible("Публикается...");
        this.driver.navigate().back();
        feedPage.waitUntilAnyElementWithContDescIsVisible("Дом");
    }

    /**
     * Метод ждет перед публикацией комментария
     * От долгого бездействия приложение может закрыться
     * Поэтому если ожидание долгое, мы каждые 10 секунд мы просто обращаемся к драйверу
     * @param commCount int номер комментария
     */
    public void checkBeforeCommentTimeout(int commCount) {
        if (System.getProperty(String.format("beforeComm%d.timeout", commCount)) != null) {
            String sec = System.getProperty(String.format("beforeComm%d.timeout", commCount));
            int timeout = Integer.parseInt(sec) * 1000;
            if (timeout > 50000) {
                int max = timeout / 10000;
                for (int i = 0; i < max; i++) {
                    feedPage.getXMLSource();
                    feedPage.waitAbit(10000);
                }
            } else
                feedPage.waitAbit(timeout);
        }
    }

    /**
     * Метод ждет перед публикацией истории
     * От долгого бездействия приложение может закрыться
     * Поэтому если ожидание долгое, мы каждые 10 секунд мы просто обращаемся к драйверу
     * @param storyCount int номер истории
     */
    public void checkBeforeStoryTimeout(int storyCount) {
        if (System.getProperty(String.format("beforeStory%d.timeout", storyCount)) != null) {
            String sec = System.getProperty(String.format("beforeStory%d.timeout", storyCount));
            int timeout = Integer.parseInt(sec) * 1000;
            if (timeout > 50000) {
                int max = timeout / 10000;
                for (int i = 0; i < max; i++) {
                    feedPage.getXMLSource();
                    feedPage.waitAbit(10000);
                }
            } else
                feedPage.waitAbit(timeout);
        }
    }

    /**
     * Метод публикует историю
     * Если история уже есть, метод ее удаляет
     */
    public void postStory() {
        feedPage.goToStoryCamera();
        if (storyGalleryPage.isElementVisibleByText("Выделить")) {
            storyGalleryPage.clickAnyElementWithText("Ещё");
            storyGalleryPage.waitUntilAnyElementWithTextIsVisible("Удалить");
            storyGalleryPage.clickAnyElementWithText("Удалить");
            storyGalleryPage.waitUntilAnyElementWithTextIsVisible("Удалить это фото?");
            storyGalleryPage.clickAnyElementWithText("Удалить");
            feedPage.waitUntilAnyElementWithContDescIsVisible("Дом");
            feedPage.waitUntilAddStoryBtnIsVisible();
            feedPage.goToStoryCamera();
        }
        feedPage.allowCameraRecording();
        feedPage.allowCameraRecording();
        feedPage.allowCameraRecording();
        feedPage.allowCameraRecording();
        feedPage.waitUntilAnyElementWithTextIsVisible("ОБЫЧНЫЙ РЕЖИМ");
        storyGalleryPage.goToGallery();
        storyGalleryPage.waitUntilAnyElementWithTextIsVisible("ВЫБРАТЬ НЕСКОЛЬКО");
        storyGalleryPage.selectPhoto(1);
        storyGalleryPage.waitUntilAnyElementWithTextIsVisible("Ваша история");
        storyGalleryPage.clickAnyElementWithText("Получатели:");
        storyGalleryPage.waitUntilAnyElementWithTextIsVisible("Поделиться");
        storyGalleryPage.share();
        storyGalleryPage.waitUntilAnyElementWithTextIsVisible("Готово");
        storyGalleryPage.clickAnyElementWithText("Готово");
        feedPage.waitUntilAnyElementWithContDescIsVisible("Дом");
    }
}
