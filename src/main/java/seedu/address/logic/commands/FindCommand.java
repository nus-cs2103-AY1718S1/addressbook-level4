package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.person.NameContainsKeywordsPredicate;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_WORD_2 = "search";
    public static final String COMMAND_WORD_3 = "get";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose details contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS]\n"
            + "Example: " + COMMAND_WORD + " alice "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        String[] parameters = (String[]) predicate.getKeywords().toArray();

        List<String> matchedNames = new ArrayList<>();
        ArrayList<String> nameList = new ArrayList<>();

        for (int i = 0; i < model.getAddressBook().getPersonList().size(); i++) {
            String[] str = model.getAddressBook().getPersonList().get(i).getName().toString().toLowerCase().split(" ");
            for (int j = 0; j < str.length; j++) {
                nameList.add(str[j]);
            }
        }

        for (int i = 0; i < parameters.length; i++) {
            for (int j = 0; j < nameList.size(); j++) {
                if (nameList.get(j).contains(parameters[i].toLowerCase())) {
                    matchedNames.add(nameList.get(j));
                }
            }
        }

        NameContainsKeywordsPredicate updatedPredicate = new NameContainsKeywordsPredicate(matchedNames);
        model.updateFilteredPersonList(updatedPredicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
