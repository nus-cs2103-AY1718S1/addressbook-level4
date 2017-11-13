package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import seedu.address.logic.commands.LoadCommand;

public class LoadCommandParserTest {

    private static String fileName = "sampleData.xml";
    private static String testFileName = "sampleDataForTheTests.xml";
    private static Path source = Paths.get("src/test/data/sandbox/" + fileName);
    private static Path destination = Paths.get("data/" + testFileName);
    private LoadCommandParser parser = new LoadCommandParser();

    /**
     * Executed before testing, copies the sample data to the correct directory
     */
    @BeforeClass
    public static void copyTestData() throws Exception {
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Executed after testing, removes the sample data
     */
    @AfterClass
    public static void deleteTestData() throws Exception {
        Files.delete(destination);
    }


    @Test
    public void parse_failure() throws Exception {
        String expectedMessage = String.format(LoadCommand.MESSAGE_ERROR_LOADING_ADDRESSBOOK,
                LoadCommand.MESSAGE_USAGE);

        // test with incorrect file name
        assertParseFailure(parser, "notAFile123456.xml", expectedMessage);
    }

}
