package guitests;

import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;
import static seedu.address.ui.testutil.GuiTestAssert.assertTaskListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.TestUtil;

public class SampleDataTest extends AddressBookGuiTest {
    @Override
    protected AddressBook getInitialData() {
        // return null to force test app to load data from file only
        return null;
    }

    @Override
    protected String getDataFileLocation() {
        // return a non-existent file location to force test app to load sample data
        String filePath = TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
        deleteFileIfExists(filePath);
        return filePath;
    }

    /**
     * Deletes the file at {@code filePath} if it exists.
     */
    private void deleteFileIfExists(String filePath) {
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException ioe) {
            throw new AssertionError(ioe);
        }
    }

    //@@author srishag
    @Test
    public void addressBook_dataFileDoesNotExist_loadSampleData() {
        Person[] expectedPersonList = SampleDataUtil.getSamplePersons();
        Task[] expectedTaskList = SampleDataUtil.getSampleTasks();
        assertListMatching(getPersonListPanel(), expectedPersonList);
        assertTaskListMatching(getTaskListPanel(), expectedTaskList);
    }
}
