import io.appium.java_client.ios.IOSDriver;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by bernardt on 28/12/16 for CalculatorTest
 */
public class NotepadTest {

    private static IOSDriver NotepadSession = null;
    private static IOSDriver ExplorerSession = null;
    private static IOSDriver DesktopSession = null;
    private static final String TargetSaveLocation = "%TEMP%";
    private static final String NOTEPAD_ID = "C:\\Windows\\System32\\notepad.exe";
    private static final String EXPLORER_ID = "C:\\Windows\\System32\\explorer.exe";
    private static final String AppDriverUrl = "http://131.169.137.143:4723";

    @BeforeClass
    public static void setup() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", NOTEPAD_ID);

        try {
            NotepadSession = new IOSDriver(new URL(AppDriverUrl), capabilities);
            NotepadSession.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void TearDown() {
//        deleteFiles();
        if (NotepadSession != null) {
            NotepadSession.quit();
        }
        NotepadSession = null;
    }

    @Test
    public void SmokeTest() {
        enterText("This is the first test text.");
        clearText();
        enterText("This is the first test text.");
        saveFile("NotepadTestFile.txt");
//        deleteFiles();
    }

    // Auxiliary Methods

    public void enterText(String inputtext)
    {
        // Enter text into the edit field
        WebElement editBox = NotepadSession.findElementByClassName("Edit");
        // Clear the edit field of any values
        editBox.sendKeys(inputtext);
        Assert.assertEquals("Compare text input and result.",inputtext, editBox.getText());

        // Enter TimeStamp
        NotepadSession.findElementByName("Edit").click();
        NotepadSession.findElementByName("Time/Date").click();
        Assert.assertNotEquals("Comparing non equal strings",inputtext, editBox.getText());
    }

    public void clearText()
    {
        NotepadSession.findElementByName("Edit").click();
        NotepadSession.findElementByName("Select All").click();
        NotepadSession.findElementByName("Edit").click();
        NotepadSession.findElementByName("Delete").click();
    }


    public void saveFile(String testfilename) {
        NotepadSession.findElementByName("File").click();
        NotepadSession.findElementByName("Save As...").click();

        try {
            Thread.sleep(1000); // Wait for 1 second until the save dialog appears
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement fileNameBox = NotepadSession.findElementByAccessibilityId("FileNameControlHost");
        fileNameBox.sendKeys(TargetSaveLocation + "\\" + testfilename);

        NotepadSession.findElementByName("Save").click();

        //
//        try {
//            Thread.sleep(2000); // Wait for 1 second until the dialog comes up
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        WebElement confirmSaveAsDialog = NotepadSession.findElementByName("Confirm Save As");
//        confirmSaveAsDialog.findElement(By.id("Yes")).click();

    }

//    public static void deleteFiles() {
//        // Launch Windows Explorer to delete the saved text file above
//        DesiredCapabilities appCapabilities = new DesiredCapabilities();
//        appCapabilities.setCapability("app", EXPLORER_ID);
//        try {
//            ExplorerSession = new IOSDriver(new URL(AppDriverUrl), appCapabilities);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        // Create Desktop session to control context menu and access dialogs
//        DesiredCapabilities desktopCapabilities = new DesiredCapabilities();
//        desktopCapabilities.setCapability("app", "Root");
//        try {
//            DesktopSession = new IOSDriver(new URL(AppDriverUrl), desktopCapabilities);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        // Navigate Windows Explorer to the target save location folder
//        try {
//            Thread.sleep(1000); // Wait for 1 second
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        WebElement addressBandRoot = ExplorerSession.findElementByClassName("Address Band Root");
//        WebElement addressToolbar = addressBandRoot.findElement(By.id("1001")); // Address Band Toolbar
////        ExplorerSession.getMouse().click(addressToolbar.Coordinates);
//        ExplorerSession.getMouse().click(addressToolbar.Coordinates);
//        addressBandRoot.findElement(By.id("41477")).sendKeys(TargetSaveLocation + Selenium.Keys.Enter);
//
//        ExplorerSession.quit();
//        ExplorerSession = null;
//        DesktopSession.quit();
//        DesktopSession = null;
//    }
}
