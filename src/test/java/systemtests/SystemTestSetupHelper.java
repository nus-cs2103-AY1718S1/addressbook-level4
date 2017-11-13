package systemtests;

import java.util.concurrent.TimeoutException;

import org.testfx.api.FxToolkit;

import guitests.guihandles.MainWindowHandle;
import seedu.address.TestApp;
import seedu.address.model.reminders.UniqueReminderList;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains helper methods that system tests require.
 */
public class SystemTestSetupHelper {
    private TestApp testApp;
    private MainWindowHandle mainWindowHandle;

    //@@author justinpoh
    /**
     * Sets up the {@code TestApp} and returns it.
     */
    public TestApp setupApplication() {
        try {
            FxToolkit.setupApplication(() -> testApp = new TestApp(TypicalPersons::getTypicalAddressBook,
                    UniqueReminderList::new, TestApp.SAVE_LOCATION_FOR_TESTING,
                    TestApp.SAVE_LOCATION_FOR_REMINDER_TESTING));
        } catch (TimeoutException te) {
            throw new AssertionError("Application takes too long to set up.");
        }

        return testApp;
    }
    //@@author

    /**
     * Initializes the stage to be used by the tests.
     */
    public static void initializeStage() {
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.hideStage();
        } catch (TimeoutException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Encapsulates the stage initialized by {@code initializeStage} in a {@code MainWindowHandle} and returns it.
     */
    public MainWindowHandle setupMainWindowHandle() {
        try {
            FxToolkit.setupStage((stage) -> {
                mainWindowHandle = new MainWindowHandle(stage);
                mainWindowHandle.focus();
            });
            FxToolkit.showStage();
        } catch (TimeoutException te) {
            throw new AssertionError("Stage takes too long to set up.");
        }

        return mainWindowHandle;
    }

    /**
     * Tears down existing stages.
     */
    public void tearDownStage() {
        try {
            FxToolkit.cleanupStages();
        } catch (TimeoutException te) {
            throw new AssertionError("Stage takes too long to tear down.");
        }
    }
}
