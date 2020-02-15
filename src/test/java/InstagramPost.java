import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.DriverManager;
import steps.AuthSteps;
import steps.FeedSteps;
import steps.GallerySteps;

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
public class InstagramPost {
    private AndroidDriver driver;
    private AuthSteps authSteps;
    private GallerySteps gallerySteps;
    private FeedSteps feedSteps;

    // тестовые данные
    private String login;
    private String pass;

    private String desc;
    private String comment;

    @BeforeClass
    public void before() {
        driver = DriverManager.getDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        DriverManager.deviceHeight = driver.manage().window().getSize().getHeight();
        DriverManager.deviceWidth = driver.manage().window().getSize().getWidth();
        DriverManager.currentWait = 10;
        authSteps = new AuthSteps(driver);
        gallerySteps = new GallerySteps(driver);
        feedSteps = new FeedSteps(driver);

        // зададим логин/пароль
        login = DriverManager.getConfig("login");
        pass = DriverManager.getConfig("pass");
    }

    @Test(enabled = true, description = "Авторизация в instagram")
    public void step_01() {
        try {
            // сначала пушим фото в галерею телефона (одинаковые файлы перезаписываются)
            gallerySteps.pushPhotosToGallery();
            // затем авторизация (внутри проходит очистка кэша)
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
            desc = String.join("\n", Files.readAllLines(path, Charset.forName("windows-1251")));
            gallerySteps.postPhoto(1, desc);
        } catch (Throwable e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test(enabled = true, description = "Публикация комментария к посту 1")
    public void step_03() {
        feedSteps.checkBeforeCommentTimeout(1);
        try {
            Path path = Files
                    .walk(Paths.get(System.getProperty("user.dir") + "/src/test/resources/comments"))
                    .filter(path1 -> path1.toFile().isFile() && path1.getFileName().toString().endsWith("1.txt"))
                    .limit(1)
                    .collect(Collectors.toList()).get(0);
            comment = String.join("\n", Files.readAllLines(path, Charset.forName("windows-1251")));
            feedSteps.postComment(desc, comment);
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
            desc = String.join("\n", Files.readAllLines(path, Charset.forName("windows-1251")));
            gallerySteps.postPhoto(2, desc);
        } catch (Throwable e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test(enabled = true, description = "Публикация комментария к посту 2")
    public void step_05() {
        feedSteps.checkBeforeCommentTimeout(2);
        try {
            Path path = Files
                    .walk(Paths.get(System.getProperty("user.dir") + "/src/test/resources/comments"))
                    .filter(path1 -> path1.toFile().isFile() && path1.getFileName().toString().endsWith("2.txt"))
                    .limit(1)
                    .collect(Collectors.toList()).get(0);
            comment = String.join("\n", Files.readAllLines(path, Charset.forName("windows-1251")));
            feedSteps.postComment(desc, comment);
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
            desc = String.join("\n", Files.readAllLines(path, Charset.forName("windows-1251")));
            gallerySteps.postPhoto(3, desc);
        } catch (Throwable e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test(enabled = true, description = "Публикация комментария к посту 3")
    public void step_07() {
        feedSteps.checkBeforeCommentTimeout(3);
        try {
            Path path = Files
                    .walk(Paths.get(System.getProperty("user.dir") + "/src/test/resources/comments"))
                    .filter(path1 -> path1.toFile().isFile() && path1.getFileName().toString().endsWith("3.txt"))
                    .limit(1)
                    .collect(Collectors.toList()).get(0);
            comment = String.join("\n", Files.readAllLines(path, Charset.forName("windows-1251")));
            feedSteps.postComment(desc, comment);
        } catch (Throwable e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
