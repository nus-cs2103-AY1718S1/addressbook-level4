package guitests;

import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.module.Code;
import seedu.address.model.module.Lesson;
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

    @Test
    public void addressBook_dataFileDoesNotExist_loadSampleData() {
        Lesson[] tempList = SampleDataUtil.getSampleLessons();
        ArrayList<Code> uniqueList = new ArrayList<>();

        for (int i = 0; i < tempList.length; i++) {
            Lesson lesson = tempList[i];
            if(!uniqueList.contains(lesson.getCode())) {
                uniqueList.add(lesson.getCode());
            }
        }

        Lesson[] expectedList = new Lesson[uniqueList.size()];
        for(int i = 0; i < uniqueList.size();i++) {
            expectedList[i] = tempList[i];
        }

        assertListMatching(getLessonListPanel(), expectedList);
    }
}
