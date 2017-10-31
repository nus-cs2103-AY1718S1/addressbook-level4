package seedu.address.ui;

import java.awt.*;
import java.awt.TrayIcon.MessageType;

/**
 * ReminderAlert allows tray icon to be utilised
 */
public class ReminderAlert {

    /**
     *
     * @param args
     * @throws AWTException
     * @throws java.net.MalformedURLException
     */
    public static void main(String[] args) throws AWTException, java.net.MalformedURLException {
        if (SystemTray.isSupported()) {
            ReminderAlert td = new ReminderAlert();
            td.displayTray();
        } else {
            System.err.println("System tray not supported!");
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
        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resizes the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);
        trayIcon.displayMessage("Hello, World", "notification demo", MessageType.INFO);
    }
}