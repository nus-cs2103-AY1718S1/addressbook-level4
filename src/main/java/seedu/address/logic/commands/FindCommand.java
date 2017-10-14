package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.SearchData;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";
    public static final int NO_RESULTS = 0;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";
    private static final String MESSAGE_DUPLICATE_PERSON = "Duplicate person tried to be added";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    /***
     * @author Sri-vatsa
     */
    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredPersonList(predicate);

        int searchResultsCount = model.getFilteredPersonList().size();

        if (searchResultsCount != NO_RESULTS) {
            for(int i = 0; i < searchResultsCount; i++) {
                ReadOnlyPerson searchedPerson = model.getFilteredPersonList().get(i);
                SearchData updatedSearchData = searchedPerson.getSearchData();
                updatedSearchData.IncrementSearchCount();
                Person modifiedPerson = new Person(searchedPerson.getName(),searchedPerson.getPhone(),
                       searchedPerson.getEmail(), searchedPerson.getAddress(),searchedPerson.getTags(),
                        updatedSearchData);

                try {
                    model.updatePerson(searchedPerson, modifiedPerson);
                } catch (DuplicatePersonException dpe) {
                    throw new CommandException(MESSAGE_DUPLICATE_PERSON);
                } catch (PersonNotFoundException pnfe) {
                    throw new AssertionError("The target person cannot be missing");
                }

            }
        }

        return new CommandResult(getMessageForPersonListShownSummary(searchResultsCount));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
