package steps;

import io.appium.java_client.android.AndroidDriver;
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

/**
 * Логика галереи
 */
public class GallerySteps {
    private AndroidDriver driver;
    private GalleryPage galleryPage;
    private NewPostPage newPostPage;

    public GallerySteps(AndroidDriver driver) {
        this.driver = driver;
        galleryPage = new GalleryPage(this.driver);
        newPostPage = new NewPostPage(this.driver);
    }

    /**
     * Метод переходит в галерею в нижнем меню
     */
    public void goToGalleryMenu() {
        galleryPage.clickBottomCamera();
        galleryPage.allowCameraRecording();
        galleryPage.waitUntilAnyElementWithTextIsVisible("ГАЛЕРЕЯ");
        galleryPage.allowCameraRecording();
        galleryPage.clickAnyElementWithText("ГАЛЕРЕЯ");
        galleryPage.waitUntilAnyElementWithTextIsVisible("Галерея");
    }

    /**
     * Метод пушит фото из ресурсов внутрь телефона
     */
    public void pushPhotosToGallery() {
        String path = System.getProperty("user.dir") + "/src/test/resources/photos";
        String storyPath = System.getProperty("user.dir") + "/src/test/resources/stories";
        List<Path> paths = new ArrayList<>();
        List<Path> storyPaths = new ArrayList<>();
        try {
            paths = Files
                    .walk(Paths.get(path))
                    .filter(path1 -> path1.toFile().isFile())
                    .sorted()
                    .collect(Collectors.toList());
            storyPaths = Files
                    .walk(Paths.get(storyPath))
                    .filter(path1 -> path1.toFile().isFile())
                    .sorted()
                    .collect(Collectors.toList());
            paths.addAll(storyPaths);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Path one : paths) {
            galleryPage.pushPhotosToGallery(
                    "/mnt/sdcard/Pictures/" + one.getFileName().toString(),
                    new File(one.toAbsolutePath().toString())
            );
        }
    }

    /**
     * Метод постит фото с текстом
     * @param desc String описание к посту
     * @param index int индекс фото
     */
    public void postPhoto(int index, String desc) {
        galleryPage.clickPhotoMini(index);
        galleryPage.clickAnyElementWithText("Далее");
        galleryPage.waitUntilAnyElementWithTextIsVisible("ФИЛЬТР");
        galleryPage.clickAnyElementWithText("Далее");
        galleryPage.waitUntilAnyElementWithTextIsVisible("Новая публикация");
        newPostPage.setDescription(desc);
        newPostPage.clickAnyElementWithText("Поделиться");
        newPostPage.waitUntilAnyElementWithContDescIsVisible("Дом");
        newPostPage.waitUntilAnyElementWithTextIsNotVisible("Завершение");
    }

    /**
     * Метод ждет перед публикацией поста
     * От долгого бездействия приложение может закрыться
     * Поэтому если ожидание долгое, мы каждые 10 секунд мы просто обращаемся к драйверу
     * @param postCount int номер поста
     */
    public void checkBeforePostTimeout(int postCount) {
        if (System.getProperty(String.format("beforePost%d.timeout", postCount)) != null) {
            String sec = System.getProperty(String.format("beforePost%d.timeout", postCount));
            int timeout = Integer.parseInt(sec) * 1000;
            if (timeout > 50000) {
                int max = timeout / 10000;
                for (int i = 0; i < max; i++) {
                    galleryPage.getXMLSource();
                    galleryPage.waitAbit(10000);
                }
            } else
                galleryPage.waitAbit(timeout);
        }
    }
}
