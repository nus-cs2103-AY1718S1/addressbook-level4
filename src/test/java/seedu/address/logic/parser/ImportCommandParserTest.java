package seedu.address.logic.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.ImportCommandParser.MESSAGE_INVALID_FILE_NAME;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_FILE_NOT_FOUND;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_DATA;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author kennard123661
public class ImportCommandParserTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseImportFilePath_invalidInput_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_FILE_NOT_FOUND);
        new ImportCommandParser().parse("Missing");
    }

    @Test
    public void parseImportFilePath_notXmlFormat_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_DATA, "./data/import/testNotXmlFormatAddressBook.xml"));
        new ImportCommandParser().parse("testNotXmlFormatAddressBook");
    }

    @Test
    public void parseImportFilePath_invalidFileName_throwsParseException() throws ParseException {
        ImportCommandParser parser = new ImportCommandParser();

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_FILE_NAME);

        parser.parse(""); // empty string
        parser.parse("ark book"); // spaces are invalid
        parser.parse("arkbook#"); // special symbols are invalid
        parser.parse("arkbook.xml"); // no .xml (.xml auto-appended)
        parser.parse("../addressBook"); // directory traversal attempt
    }

    @Test
    public void parseImportFilePath_validInput_success() throws ParseException {
        ImportCommandParser parser = new ImportCommandParser();

        ImportCommand importCommand = parser.parse("testValidAddressBook");
        assertTrue(importCommand instanceof ImportCommand);

        importCommand = parser.parse("testAddressBookForImportSystem");
        assertTrue(importCommand instanceof ImportCommand);
    }

    @Test
    public void isValidFileName() throws ParseException {
        assertTrue(ImportCommandParser.isValidFileName("123")); // only numeric
        assertTrue(ImportCommandParser.isValidFileName("arkbook")); // only alphabetical
        assertTrue(ImportCommandParser.isValidFileName("_______")); // only underscore

        assertTrue(ImportCommandParser.isValidFileName("arkbook123")); // alphabetical and numeric
        assertTrue(ImportCommandParser.isValidFileName("ark_today")); // alphabetical and number
        assertTrue(ImportCommandParser.isValidFileName("12_34")); // alphabetical and underscore

        assertFalse(ImportCommandParser.isValidFileName("ark book")); // spaces are invalid
        assertFalse(ImportCommandParser.isValidFileName("arkbook#")); // special symbols are invalid
        assertFalse(ImportCommandParser.isValidFileName("arkbook.xml")); // no .xml (.xml auto-appended)
    }

}
