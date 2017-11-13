package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    //@@author dalessr
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        //Throw an error if there is no argument followed by the command word
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        //Get the index range of different keywords (distinguished by attributes) from trimmedArgs
        int indexOfName = trimmedArgs.indexOf(PREFIX_NAME.getPrefix());
        int indexOfPhone = trimmedArgs.indexOf(PREFIX_PHONE.getPrefix());
        int indexOfEmail = trimmedArgs.indexOf(PREFIX_EMAIL.getPrefix());
        int indexOfAddress = trimmedArgs.indexOf(PREFIX_ADDRESS.getPrefix());
        //Throw an error if there is no prefixes to specify the type of the keywords
        if ((indexOfName == -1) && (indexOfPhone == -1) && (indexOfEmail == -1) && (indexOfAddress == -1)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        String symbolAtStart = trimmedArgs.substring(0, 2);
        //Throw an error if there is some dummy values before the first prefix after the command word
        if ((!symbolAtStart.equals(PREFIX_NAME.getPrefix())) && (!symbolAtStart.equals(PREFIX_PHONE.getPrefix()))
                && (!symbolAtStart.equals(PREFIX_EMAIL.getPrefix()))
                && (!symbolAtStart.equals(PREFIX_ADDRESS.getPrefix()))) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        int[] attributeIndexArray = {indexOfName, indexOfPhone, indexOfEmail, indexOfAddress, trimmedArgs.length()};
        Arrays.sort(attributeIndexArray);
        //Put different types of keywords into separate strings
        String trimmedNames = null;
        if (indexOfName != -1) {
            int index = 0;
            for (int i = 0; i < attributeIndexArray.length; i++) {
                if (attributeIndexArray[i] == indexOfName) {
                    index = i;
                    break;
                }
            }
            trimmedNames = trimmedArgs.substring(indexOfName + 2, attributeIndexArray[index + 1]).trim();
            if (trimmedNames.equals("")) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }

        String trimmedPhones = null;
        if (indexOfPhone != -1) {
            int index = 0;
            for (int i = 0; i < attributeIndexArray.length; i++) {
                if (attributeIndexArray[i] == indexOfPhone) {
                    index = i;
                    break;
                }
            }
            trimmedPhones = trimmedArgs.substring(indexOfPhone + 2, attributeIndexArray[index + 1]).trim();
            if (trimmedPhones.equals("")) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }

        String trimmedEmails = null;
        if (indexOfEmail != -1) {
            int index = 0;
            for (int i = 0; i < attributeIndexArray.length; i++) {
                if (attributeIndexArray[i] == indexOfEmail) {
                    index = i;
                    break;
                }
            }
            trimmedEmails = trimmedArgs.substring(indexOfEmail + 2, attributeIndexArray[index + 1]).trim();
            if (trimmedEmails.equals("")) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }

        String trimmedAddress = null;
        if (indexOfAddress != -1) {
            int index = 0;
            for (int i = 0; i < attributeIndexArray.length; i++) {
                if (attributeIndexArray[i] == indexOfAddress) {
                    index = i;
                    break;
                }
            }
            trimmedAddress = trimmedArgs.substring(indexOfAddress + 2, attributeIndexArray[index + 1]).trim();
            if (trimmedAddress.equals("")) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }
        //Add all the keywords to a list distinguished by prefixes in order to parse them to FindCommand class
        List<String> keyWordsToSearch = new ArrayList<>();

        if (trimmedNames != null) {
            String[] nameKeywords = trimmedNames.split(" ");
            keyWordsToSearch.add(PREFIX_NAME.getPrefix());
            for (int i = 0; i < nameKeywords.length; i++) {
                keyWordsToSearch.add(nameKeywords[i]);
            }
        }

        if (trimmedPhones != null) {
            String[] phoneKeywords = trimmedPhones.split(" ");
            keyWordsToSearch.add(PREFIX_PHONE.getPrefix());
            for (int i = 0; i < phoneKeywords.length; i++) {
                keyWordsToSearch.add(phoneKeywords[i]);
            }
        }

        if (trimmedEmails != null) {
            String[] emailKeywords = trimmedEmails.split(" ");
            keyWordsToSearch.add(PREFIX_EMAIL.getPrefix());
            for (int i = 0; i < emailKeywords.length; i++) {
                keyWordsToSearch.add(emailKeywords[i]);
            }
        }

        if (trimmedAddress != null) {
            String[] addressKeywords = trimmedAddress.split(" ");
            keyWordsToSearch.add(PREFIX_ADDRESS.getPrefix());
            for (int i = 0; i < addressKeywords.length; i++) {
                keyWordsToSearch.add(addressKeywords[i]);
            }
        }

        String[] parameters = new String[keyWordsToSearch.size()];
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = keyWordsToSearch.get(i);
        }
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList(parameters));
        return new FindCommand(predicate);
    }
}
