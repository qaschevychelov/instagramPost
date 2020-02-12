import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.BasePage;
import steps.AuthSteps;
import steps.GallerySteps;
import pages.DriverManager;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.Assert.fail;

/**
 * Автотест для Instagram
 */
public class InstagramPost extends BasePage {
    private AndroidDriver driver;
    private AuthSteps authSteps;
    private GallerySteps gallerySteps;

    // тестовые данные
    private String login = "9623798819";
    private String pass = "Kaz54671219";

    @BeforeClass
    public void before() {
        driver = DriverManager.getDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        authSteps = new AuthSteps(driver);
        gallerySteps = new GallerySteps(driver);
    }

    @Test(enabled = true, description = "Авторизация в instagram")
    public void step_01() {
        try {
            // сначала пушим фото в галерею телефона (одинаковые файлы перезаписываются)
            gallerySteps.pushPhotosToGallery();
            // затем авторизация (внутри проходит очистка кэша)
            // повторно
            // это необходимо для постоянно контролируемого поведения
            authSteps.auth(login, pass);
        } catch (Throwable e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test(enabled = true, description = "Публикация поста 1 в instagram")
    public void step_02() {
        try {
            gallerySteps.goToGalleryMenu();
            Path path = Files
                        .walk(Paths.get(System.getProperty("user.dir") + "/src/test/resources/postDescription"))
                        .filter(path1 -> path1.toFile().isFile() && path1.getFileName().toString().endsWith("1.txt"))
                        .limit(1)
                        .collect(Collectors.toList()).get(0);
            String desc = String.join("\n", Files.readAllLines(path, Charset.forName("windows-1251")));
            gallerySteps.postPhoto(1, desc);
        } catch (Throwable e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test(enabled = true, description = "Публикация поста 2 в instagram")
    public void step_04() {
        try {
            gallerySteps.checkBeforePostTimeout(2);
            gallerySteps.goToGalleryMenu();
            Path path = Files
                    .walk(Paths.get(System.getProperty("user.dir") + "/src/test/resources/postDescription"))
                    .filter(path1 -> path1.toFile().isFile() && path1.getFileName().toString().endsWith("2.txt"))
                    .limit(1)
                    .collect(Collectors.toList()).get(0);
            String desc = String.join("\n", Files.readAllLines(path, Charset.forName("windows-1251")));
            gallerySteps.postPhoto(2, desc);
        } catch (Throwable e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test(enabled = true, description = "Публикация поста 3 в instagram")
    public void step_06() {
        try {
            gallerySteps.checkBeforePostTimeout(3);
            gallerySteps.goToGalleryMenu();
            Path path = Files
                    .walk(Paths.get(System.getProperty("user.dir") + "/src/test/resources/postDescription"))
                    .filter(path1 -> path1.toFile().isFile() && path1.getFileName().toString().endsWith("3.txt"))
                    .limit(1)
                    .collect(Collectors.toList()).get(0);
            String desc = String.join("\n", Files.readAllLines(path, Charset.forName("windows-1251")));
            gallerySteps.postPhoto(3, desc);
        } catch (Throwable e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
