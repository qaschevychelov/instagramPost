package steps;

import io.appium.java_client.android.AndroidDriver;
import pages.FeedPage;
import pages.GalleryPage;
import pages.NewPostPage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FeedSteps {
    AndroidDriver driver;
    FeedPage feedPage;

    public FeedSteps(AndroidDriver driver) {
        this.driver = driver;
        feedPage = new FeedPage(this.driver);
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
}
