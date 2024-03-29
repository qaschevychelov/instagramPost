package steps;

import io.appium.java_client.android.AndroidDriver;
import pages.FeedPage;
import pages.SettingsPage;
import pages.StoryGalleryPage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static io.appium.java_client.touch.offset.PointOption.point;

/**
 * ������ �����
 */
public class FeedSteps {
    private AndroidDriver driver;
    private FeedPage feedPage;
    private StoryGalleryPage storyGalleryPage;
    private SettingsPage settingsPage;

    public FeedSteps(AndroidDriver driver) {
        this.driver = driver;
        feedPage = new FeedPage(this.driver);
        storyGalleryPage = new StoryGalleryPage(this.driver);
        settingsPage = new SettingsPage(this.driver);
    }


    /**
     * ����� ������ ����������� � �����
     * @param desc String �������� �����
     * @param comment String ��� �����������
     */
    public void postComment(String desc, String comment) {
        feedPage.findPost(desc);
        feedPage.goToComments(desc);
        feedPage.waitUntilAnyElementWithTextIsVisible("�������� �����������...");
        feedPage.hideAndroidKeyboard();
        feedPage.setComment(comment);
        feedPage.clickAnyElementWithText("������������");
        feedPage.waitUntilAnyElementWithTextIsNotVisible("�����������...");
        if (feedPage.isElementVisibleByText("�� ������� ������������ �����������") || feedPage.isElementVisibleByText("�������� �������������")) {
            this.driver.navigate().back();
            feedPage.waitUntilAnyElementWithTextIsNotVisible("�� ������� ������������ �����������");
        }
        this.driver.navigate().back();
        feedPage.waitUntilAnyElementWithContDescIsVisible("���");
    }

    /**
     * ����� ���� ����� ����������� �����������
     * �� ������� ����������� ���������� ����� ���������
     * ������� ���� �������� ������, �� ������ 10 ������ �� ������ ���������� � ��������
     * @param commCount int ����� �����������
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
     * ����� ���� ����� ����������� �������
     * �� ������� ����������� ���������� ����� ���������
     * ������� ���� �������� ������, �� ������ 10 ������ �� ������ ���������� � ��������
     * @param storyCount int ����� �������
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
     * ����� ��������� �������
     * ���� ������� ��� ����, ����� �� �������
     */
    public void postStory() {
        feedPage.goToStoryCamera();
        if (storyGalleryPage.isElementVisibleByContainsTexts("��������, ����� ���������") || storyGalleryPage.isElementVisibleByText("��������")) {
            storyGalleryPage.clickAnyElementWithText("���");
            storyGalleryPage.waitUntilAnyElementWithTextIsVisible("�������");
            storyGalleryPage.clickAnyElementWithText("�������");
            storyGalleryPage.waitUntilAnyElementWithTextIsVisible("������� ��� ����?");
            storyGalleryPage.clickAnyElementWithText("�������");
            feedPage.waitUntilAnyElementWithContDescIsVisible("���");
            // �������� ������� �� ������ ����� �����������, ���� ���� �������� � �������������������
            // ������� ��������
            try {
                feedPage.waitUntilAddStoryBtnIsVisible();
            } catch (Throwable e) {
                feedPage.goToStoryCamera();
                storyGalleryPage.waitUntilAnyElementWithTextIsVisible("���");
                storyGalleryPage.clickAnyElementWithText("���");
                storyGalleryPage.waitUntilAnyElementWithTextIsVisible("�������");
                storyGalleryPage.clickAnyElementWithText("�������");
                storyGalleryPage.waitUntilAnyElementWithTextIsVisible("������� ��� ����?");
                storyGalleryPage.clickAnyElementWithText("�������");
                feedPage.waitUntilAnyElementWithContDescIsVisible("���");
                feedPage.waitUntilAddStoryBtnIsVisible();
            }
            feedPage.goToStoryCamera();
        }
        // ����� ����� ����������
        feedPage.allowCameraRecording();
        feedPage.waitUntilAnyElementWithTextIsVisible("������� �����");
        storyGalleryPage.goToGallery();
        storyGalleryPage.waitUntilAnyElementWithTextIsVisible("������� ���������");
        storyGalleryPage.selectPhoto(1);
        storyGalleryPage.waitUntilAnyElementWithTextIsVisible("���� �������");
        storyGalleryPage.clickAnyElementWithText("����������:");
        storyGalleryPage.waitUntilAnyElementWithTextIsVisible("����������");
        storyGalleryPage.share();
        storyGalleryPage.waitUntilAnyElementWithTextIsVisible("������");
        storyGalleryPage.clickAnyElementWithText("������");
        feedPage.waitUntilAnyElementWithContDescIsVisible("���");
    }

    /**
     * ����� ��������� ���������� ���� ����� ����������
     */
    public void turnOffSavings() {
        feedPage.clickAnyElementWithContDesc("�������");
        feedPage.waitUntilAnyElementWithTextIsVisible("������������� �������");
        feedPage.clickAnyElementWithContDesc("���������");
        feedPage.waitUntilAnyElementWithTextIsVisible("���������");
        feedPage.clickAnyElementWithText("���������");
        feedPage.waitUntilAnyElementWithTextIsVisible("�������");
        feedPage.clickAnyElementWithText("�������");
        feedPage.waitUntilAnyElementWithTextIsVisible("������������ ����������");
        feedPage.clickAnyElementWithText("������������ ����������");
        feedPage.waitUntilAnyElementWithTextIsVisible("������������ ����������");
        settingsPage.turnOffSavings();
        settingsPage.getDriver().navigate().back();
        settingsPage.waitUntilAnyElementWithTextIsVisible("�������");
        settingsPage.getDriver().navigate().back();
        settingsPage.waitUntilAnyElementWithTextIsVisible("���������");
        settingsPage.getDriver().navigate().back();
        feedPage.waitUntilAnyElementWithTextIsVisible("������������� �������");
        feedPage.clickAnyElementWithContDesc("���");
        feedPage.waitUntilAnyElementWithTextsIsVisible("���� �������[���]����� ���������� � Instagram");
    }

    /**
     * ����� ����������� �� ����� � ��������� 3 ������� ����� � /target/feed/
     */
    public void walkThroughFeed() {
        for (int i = 0; i < 3; i++) {
            feedPage.normalizePostImg();
            feedPage.savePostImg("target/feed/postImg" + (i +1) + ".png");
            String desc = feedPage.findPostDescription();
            File fileDesc = new File("target/feed/postDesc" + (i +1) + ".txt");
            if (fileDesc.isFile()) fileDesc.delete();
            try {
                if (!fileDesc.isFile() && fileDesc.createNewFile()) {
                    //��������� � ����
                    BufferedWriter writer = new BufferedWriter(new FileWriter(fileDesc));
                    writer.write(desc);
                    writer.flush();
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String comm = feedPage.findCommDescription();
            File fileComm = new File("target/feed/postComm" + (i +1) + ".txt");
            if (fileComm.isFile()) fileComm.delete();
            try {
                if (!fileComm.isFile() && fileComm.createNewFile()) {
                    //��������� � ����
                    BufferedWriter writer = new BufferedWriter(new FileWriter(fileComm));
                    writer.write(comm);
                    writer.flush();
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            feedPage.goToNextPost();
        }
    }
}
