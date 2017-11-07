package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ImportFileChooseEvent;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author freesoup
public class ImportCommandParserTest {

    private ImportCommandParser parser = new ImportCommandParser();

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void parse_wrongFileFormat_throwsParseException() {
        //Wrong file type
        assertParseFailure(parser, "wrong.asd", ImportCommand.MESSAGE_WRONG_FORMAT);

        //No file type
        assertParseFailure(parser, "wrong", ImportCommand.MESSAGE_WRONG_FORMAT);
    }

    @Test
    public void parse_corruptedFile_throwsParseException() {
        //Corrupted xml
        assertParseFailure(parser, "error.xml", ImportCommand.MESSAGE_FILE_CORRUPT);

        //Corrupted vcf
        assertParseFailure(parser, "error.vcf", ImportCommand.MESSAGE_FILE_CORRUPT);
    }

    @Test
    public void parse_noArgsCancelImport_throwsParseException() {
        //Opens file explorer and choose to not select a file.
        assertParseFailure(parser, " ", ImportCommand.MESSAGE_IMPORT_CANCELLED);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ImportFileChooseEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void parse_validArgs_success() {
        //Valid xml file with data of getTypicalAddressBook.
        assertParseSuccess(parser, "out1.xml", new ImportCommand(getTypicalAddressBook().getPersonList()));
    }
}
