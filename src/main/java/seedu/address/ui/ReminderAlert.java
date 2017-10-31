package seedu.address.ui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.net.MalformedURLException;

import javax.swing.plaf.synth.Region;

/**
 * ReminderAlert allows tray icon to be utilised
 */
public class ReminderAlert extends UiPart<Region> {


    public ReminderAlert() {
        try {
            if (SystemTray.isSupported()) {
                ReminderAlert td = new ReminderAlert();
                td.displayTray();
            } else {
                System.err.println("System tray not supported!");
            }
        } catch (AWTException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Method utilised to display tray icon
     */
    public void displayTray() throws AWTException, java.net.MalformedURLException {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getToolkit().createImage(getClass().getResource("icon.png"));
        TrayIcon trayIcon = new TrayIcon(image, "Reminder");
        //Let the system resizes the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);
        trayIcon.displayMessage("You have an event today!", "CS2103T Lecture", MessageType.INFO);
    }
}
