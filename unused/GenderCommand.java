package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

//@@author eeching
/**
 * Adds or updates the birthday of a person identified using it's last displayed index from the address book.
 */
public class GenderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "gender";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Specify the person's gender identified by the index number used in the last person listing.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer)\n"
            + PREFIX_GENDER + "GENDER"
            + "Example: " + COMMAND_WORD + " 1" + " g/" + "male";

    public static final String MESSAGE_UPDATE_PERSON_GENDER_SUCCESS = "Updated Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;

    private final Gender gender;

    public GenderCommand(Index targetIndex, Gender gender) {
        this.targetIndex = targetIndex;
        this.gender = gender;
    }

    /**
     * Adds or Updates a Person's birthday
     */
    private Person updatePersonGender(ReadOnlyPerson personToUpdateGender, Gender gender) {
        Name name = personToUpdateGender.getName();
        Phone phone = personToUpdateGender.getPhone();
        Email email = personToUpdateGender.getEmail();
        Address address = personToUpdateGender.getAddress();
        Set<Tag> tags = personToUpdateGender.getTags();

        Person personUpdated = new Person(name, phone, email, address, gender, tags);

        return personUpdated;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToUpdateGender = lastShownList.get(targetIndex.getZeroBased());
        Person personUpdated = updatePersonGender(personToUpdateGender, gender);
        //System.out.println(personUpdated.getGender() + " " + personUpdated.getGender());

        try {
            model.updatePerson(personToUpdateGender, personUpdated);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_UPDATE_PERSON_GENDER_SUCCESS, personUpdated));
    }
}