package steps;

import io.appium.java_client.android.AndroidDriver;
import pages.FeedPage;

/**
 * ������ �����
 */
public class FeedSteps {
    private AndroidDriver driver;
    private FeedPage feedPage;

    public FeedSteps(AndroidDriver driver) {
        this.driver = driver;
        feedPage = new FeedPage(this.driver);
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
}
