package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.LoadCommand;

import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.XmlAddressBookStorage;

/**
 * Parses input arguments and creates a new LoadCommand object
 */
public class LoadCommandParser implements Parser<LoadCommand> {

    private final String filePath = "data/";

    /**
     * Parses the given {@code String} of arguments in the context of the LoadCommand
     * and returns an LoadCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoadCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
        }

        String filePathWithFileName = filePath + trimmedArgs;

        XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(filePathWithFileName);

        Optional<ReadOnlyAddressBook> inputtedAddressBook;
        try {
            inputtedAddressBook = addressBookStorage.readAddressBook();
        } catch (DataConversionException e) {
            throw new ParseException(LoadCommand.MESSAGE_ERROR_LOADING_ADDRESSBOOK);
        } catch (IOException e) {
            throw new ParseException(LoadCommand.MESSAGE_ERROR_LOADING_ADDRESSBOOK);
        }

        return new LoadCommand(inputtedAddressBook.orElseThrow(() -> new ParseException(
            LoadCommand.MESSAGE_ERROR_LOADING_ADDRESSBOOK)));
    }

}

