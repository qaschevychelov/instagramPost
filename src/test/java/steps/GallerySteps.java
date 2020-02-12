package steps;

import io.appium.java_client.android.AndroidDriver;
import pages.AuthPage;
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

public class GallerySteps {
    AndroidDriver driver;
    GalleryPage galleryPage;
    NewPostPage newPostPage;

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
        List<Path> paths = new ArrayList<>();
        try {
            paths = Files
                    .walk(Paths.get(path))
                    .filter(path1 -> path1.toFile().isFile())
                    .sorted()
                    .collect(Collectors.toList());
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
            long timeout = Integer.parseInt(sec) * 1000;
            if (timeout > 50000) {
                long max = timeout / 10000;
                for (long i = 0; i < max; i++) {
                    galleryPage.getDriver().getPageSource();
                    galleryPage.waitAbit(10000);
                }
            } else
                galleryPage.waitAbit(timeout);
        }
    }
}
