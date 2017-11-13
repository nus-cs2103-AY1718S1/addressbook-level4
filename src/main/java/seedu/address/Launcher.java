package seedu.address;

import com.sun.javafx.application.LauncherImpl;

//@@author fongwz
/**
 * Launches the splash screen before mainapp is started
 */
public class Launcher {
    public static void main(String[] args) {
        LauncherImpl.launchApplication(MainApp.class, FirstPreloader.class, args);
    }
}
