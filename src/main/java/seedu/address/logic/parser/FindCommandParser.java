package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FieldContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

//@@author NabeelZaheer
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
        if (trimmedArgs.isEmpty() || !((trimmedArgs.contains(PREFIX_NAME.getPrefix()))
                || (trimmedArgs.contains(PREFIX_PHONE.getPrefix()))
                || (trimmedArgs.contains(PREFIX_EMAIL.getPrefix()))
                || (trimmedArgs.contains(PREFIX_ADDRESS.getPrefix()))
                || (trimmedArgs.contains(PREFIX_TAG.getPrefix())))) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        StringTokenizer st = new StringTokenizer(trimmedArgs, " ");
        String newArgs = " ";
        String current = "";
        boolean exitAppend = false;
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if ((token.contains(PREFIX_NAME.getPrefix())) || (token.contains(PREFIX_PHONE.getPrefix()))
                    || (token.contains(PREFIX_EMAIL.getPrefix())) || (token.contains(PREFIX_ADDRESS.getPrefix()))
                    || (token.contains(PREFIX_TAG.getPrefix()))) {
                current = token.substring(0, 2);
                newArgs += token + " ";
                if (token.length() != 2) {
                    exitAppend = true;
                } else {
                    exitAppend = false;
                }
            } else {
                if (!exitAppend) {
                    int length = newArgs.length();
                    newArgs = newArgs.substring(0, length - 3);
                }
                newArgs += current + token + " ";
            }



        }
        String trimmedNewArgs = newArgs.trim();

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(newArgs, PREFIX_TAG, PREFIX_EMAIL, PREFIX_NAME,
                        PREFIX_PHONE, PREFIX_ADDRESS);

        List<String> nameList = argMultimap.getAllValues(PREFIX_NAME);
        List<String> phoneList = argMultimap.getAllValues(PREFIX_PHONE);
        List<String> emailList = argMultimap.getAllValues(PREFIX_EMAIL);
        List<String> addressList = argMultimap.getAllValues(PREFIX_ADDRESS);
        List<String> tagList = argMultimap.getAllValues(PREFIX_TAG);

        List<List<String>> keywords = new ArrayList<>();
        keywords.add(nameList);
        keywords.add(phoneList);
        keywords.add(emailList);
        keywords.add(addressList);
        keywords.add(tagList);

        List<Prefix> prefixList = new ArrayList<>();
        prefixList.add(PREFIX_NAME);
        prefixList.add(PREFIX_PHONE);
        prefixList.add(PREFIX_EMAIL);
        prefixList.add(PREFIX_ADDRESS);
        prefixList.add(PREFIX_TAG);

        String missingInput = "";
        Boolean checkMissingInput = false;
        for (int i = 0; i < keywords.size(); i++) {
            List<String> list = keywords.get(i);
            String prefix = prefixList.get(i).getPrefix();
            if (checkNoInput(list) && (trimmedNewArgs.contains(prefix))) {
                missingInput += prefix + " ";
                checkMissingInput = true;
            }
        }
        if (checkMissingInput) {
            throw new ParseException("Missing input for field: " + missingInput + "\n"
                    + FindCommand.MESSAGE_USAGE);
        }

        Predicate<ReadOnlyPerson> predicate =
                new FieldContainsKeywordsPredicate(keywords);
        return new FindCommand(predicate);
    }
    //@@author

    /**
     *
     * @param list
     * @return true of list does not contain any executable input
     */
    private Boolean checkNoInput(List<String> list) {
        Iterator<String> it = list.iterator();
        Boolean check = true;

        while (it.hasNext()) {
            String toCheck = it.next();
            if (!toCheck.isEmpty()) {
                check = false;
            }
        }
        return check;
    }

}

