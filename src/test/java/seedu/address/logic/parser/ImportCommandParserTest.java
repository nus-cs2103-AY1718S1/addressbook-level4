package seedu.address.logic.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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
    public void parseImportFilePath_invalidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE)
                + "\nMore Info: " + MESSAGE_FILE_NOT_FOUND);
        new ImportCommandParser().parse("Missing");
    }

    @Test
    public void parseImportFilePath_notXmlFormat_throwsIllegalValueException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE)
                + "\nMore Info: " + String.format(MESSAGE_INVALID_DATA,
                "./data/import/testNotXmlFormatAddressBook.xml"));

        new ImportCommandParser().parse("testNotXmlFormatAddressBook");
    }

    @Test
    public void parseImportFilePath_invalidFileName_throwsIllegalValueException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(ImportCommandParser.MESSAGE_FILE_NAME_INVALID);

        new ImportCommandParser().parse("#!Hfas");

        // directory traversal attempt
        thrown.expect(ParseException.class);
        thrown.expectMessage(ImportCommandParser.MESSAGE_FILE_NAME_INVALID);

        new ImportCommandParser().parse("../addressBook");
    }

    @Test
    public void parseImportFilePath_validInput_success() throws Exception {
        ImportCommand importCommand = new ImportCommandParser().parse("testValidAddressBook");
        assertTrue(importCommand instanceof ImportCommand);
    }

    @Test
    public void isValidFileName() throws Exception {
        assertTrue(ImportCommandParser.isValidFileName("123"));
        assertTrue(ImportCommandParser.isValidFileName("arkbook"));
        assertTrue(ImportCommandParser.isValidFileName("arkbook123"));
        assertTrue(ImportCommandParser.isValidFileName("123arkbook123"));
        assertTrue(ImportCommandParser.isValidFileName("ark_today"));
        assertTrue(ImportCommandParser.isValidFileName("test_today123"));

        assertFalse(ImportCommandParser.isValidFileName("ark test")); // spaces are invalid
        assertFalse(ImportCommandParser.isValidFileName("1#!*#(!")); // special symbols are invalid
        assertFalse(ImportCommandParser.isValidFileName("ark.xml")); // invalid
    }

}
