package seedu.address.logic.commands;
//@@author jeffreygohkw
import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.ShowAllPrivacyLevelPredicate;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Handles the changing of Privacy Levels in the address book
 */
public class PrivacyLevelCommand extends Command {

    public static final String COMMAND_WORD = "privacylevel";
    public static final String COMMAND_ALIAS = "pl";

    public static final String CHANGE_PRIVACY_LEVEL_SUCCESS = "Successfully change privacy level to %1$s.";
    public static final String WRONG_PRIVACY_LEVEL_MESSAGE = "Privacy Level can only be 1, 2 or 3";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    public static final int MIN_PRIVACY_LEVEL = 1;
    public static final int MAX_PRIVACY_LEVEL = 3;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the privacy level of the address book. Level 1 shows all data, level 2 hides private fields"
            + " and level 3 hides persons with at least 1 private field.\n"
            + "Parameters: LEVEL (must be 1, 2 or 3)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final int level;

    public PrivacyLevelCommand(int level) {
        this.level = level;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        if (level < MIN_PRIVACY_LEVEL || level > MAX_PRIVACY_LEVEL) {
            throw new CommandException(WRONG_PRIVACY_LEVEL_MESSAGE);
        }
        model.setPrivacyLevel(level);
        for (int i = 0; i < model.getAddressBook().getPersonList().size(); i++) {
            ReadOnlyPerson toReplace = model.getPersonAtIndexFromAddressBook(i);
            Person newPerson = new Person(toReplace);
            newPerson.setPrivacyLevel(level);
            try {
                model.updatePerson(toReplace, newPerson);
            } catch (DuplicatePersonException e) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException e) {
                throw new AssertionError("The target person cannot be missing");
            }
        }
        if (level == 3) {
            model.updateFilteredPersonList(new ShowAllPrivacyLevelPredicate());
        } else {
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        }
        return new CommandResult(String.format(CHANGE_PRIVACY_LEVEL_SUCCESS, Integer.toString(level)));
    }

    public int getLevel() {
        return level;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PrivacyLevelCommand // instanceof handles nulls
                && this.level == ((PrivacyLevelCommand) other).level); // state check
    }
}
