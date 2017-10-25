package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.LoadCommand;

import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.storage.XmlAddressBookStorage;

/**
 * Parses input arguments and creates a new LoadCommand object
 */
public class LoadCommandParser implements Parser<LoadCommand> {

    private String filePath = "data/";

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

        return new LoadCommand(addressBookStorage);

    }

}

