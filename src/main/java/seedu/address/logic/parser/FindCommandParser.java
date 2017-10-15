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

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        int indexOfName = trimmedArgs.indexOf(PREFIX_NAME.getPrefix());
        int indexOfPhone = trimmedArgs.indexOf(PREFIX_PHONE.getPrefix());
        int indexOfEmail = trimmedArgs.indexOf(PREFIX_EMAIL.getPrefix());
        int indexOfAddress = trimmedArgs.indexOf(PREFIX_ADDRESS.getPrefix());
        int[] attributeIndexArray = {indexOfName, indexOfPhone, indexOfEmail, indexOfAddress, trimmedArgs.length()};
        Arrays.sort(attributeIndexArray);

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
        }

        List<String> namesToSearch = new ArrayList<>();

        if (trimmedNames != null) {
            String[] nameKeywords = trimmedNames.split(" ");
            namesToSearch.add(PREFIX_NAME.getPrefix());
            for (int i = 0; i < nameKeywords.length; i++) {
                namesToSearch.add(nameKeywords[i]);
            }
        }

        if (trimmedPhones != null) {
            String[] phoneKeywords = trimmedPhones.split(" ");
            namesToSearch.add(PREFIX_PHONE.getPrefix());
            for (int i = 0; i < phoneKeywords.length; i++) {
                namesToSearch.add(phoneKeywords[i]);
            }
        }

        if (trimmedEmails != null) {
            String[] emailKeywords = trimmedEmails.split(" ");
            namesToSearch.add(PREFIX_EMAIL.getPrefix());
            for (int i = 0; i < emailKeywords.length; i++) {
                namesToSearch.add(emailKeywords[i]);
            }
        }

        if (trimmedAddress != null) {
            String[] addressKeywords = trimmedAddress.split(" ");
            namesToSearch.add(PREFIX_ADDRESS.getPrefix());
            for (int i = 0; i < addressKeywords.length; i++) {
                namesToSearch.add(addressKeywords[i]);
            }
        }

        String[] parameters = new String[namesToSearch.size()];
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = namesToSearch.get(i);
        }

        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList(parameters));
        return new FindCommand(predicate);
    }
    /**
     * Get a list of names who has the certain attributes provided by user.
     * @param prefix the attribute to search from the storage
     * @param keywords the information to search for based on the prefix given
     * @return a list of names contain at least one keyword
     */
    private ArrayList<String> getNamesToSearch(Prefix prefix, String[] keywords) {
        ArrayList<String> nameList = new ArrayList<>();

        if (prefix.getPrefix().equals(PREFIX_PHONE.getPrefix())) {

        } else if (prefix.getPrefix().equals(PREFIX_EMAIL.getPrefix())) {

        }
        if (nameList.size() == 0) {
            return null;
        } else {
            return nameList;
        }
    }

    private ArrayList<String> getNamesToSearch(String trimmedAddress) {
        return null;
    }

}
