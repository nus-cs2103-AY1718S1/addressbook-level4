package seedu.address.logic.commands;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.user.exceptions.DuplicateUserException;

//@@author kaiyu92
/**
 * Export data into csv format
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Export the data either from the address book or event book.\n"
            + "Parameters: BOOK (either addressbook or eventbook)\n"
            + "Example: " + COMMAND_WORD + " addressbook";

    public static final String MESSAGE_EXPORT_BOOK_SUCCESS = "Successfully Exported";
    public static final String MESSAGE_EXPORT_BOOK_ERROR = "Export failed. Please check whether the xml file exist";

    public static final String[] BOOK_VALIDATION = {"addressbook", "eventbook"};

    private final String targetBook;

    public ExportCommand(String targetBook) {
        this.targetBook = targetBook;
    }

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute() throws CommandException, DuplicateUserException {

        try {
            if (BOOK_VALIDATION[0].equals(targetBook)) {
                model.exportAddressBook();
            } else {
                model.exportEventBook();
            }

        } catch (IOException ioe) {
            throw new AssertionError("Xml File cannot be missing");
        } catch (ParserConfigurationException pce) {
            throw new AssertionError("Parser cannot be invalid");
        } catch (SAXException se) {
            throw new AssertionError("XML Document must be valid");
        } catch (TransformerException te) {
            throw new AssertionError("Able to produce new file");
        }

        return new CommandResult(MESSAGE_EXPORT_BOOK_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && this.targetBook.equals(((ExportCommand) other).targetBook)); // state check
    }
}
