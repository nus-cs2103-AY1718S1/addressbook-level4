# Giang
###### /java/seedu/address/logic/commands/RemarkCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Add a remark for a specific person
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String MESSAGE_REMARK_PERSON_SUCCESS = "Remark: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a remark to a person to the address book. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "REMARK "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Best friends ";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Remark remark;
    private final Index targetIndex;

    public RemarkCommand(Index index, Remark remark) {
        this.targetIndex = index;
        this.remark = remark;
    }
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToRemark = lastShownList.get(targetIndex.getZeroBased());
        Person remarkedPerson = addOrChangeRemark(personToRemark, this.remark);
        remarkedPerson.setFavorite(personToRemark.getFavorite());

        try {
            model.updatePerson(personToRemark, remarkedPerson);
            model.propagateToGroup(personToRemark, remarkedPerson, this.getClass());
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_REMARK_PERSON_SUCCESS, remarkedPerson));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemarkCommand // instanceof handles nulls
                && this.targetIndex.equals(((RemarkCommand) other).targetIndex)); // state check
    }

    /**
     * Generate a new person with all attributes from the readonly person, but add or change remark
     * @param person Readonly person to be remarked/editted
     * @param remark new Remark object to be insert
     * @return a new Readonly person with the remark object
     */
    public static Person addOrChangeRemark(ReadOnlyPerson person, Remark remark) {
        Name updatedName = person.getName();
        Phone updatedPhone = person.getPhone();
        Email updatedEmail = person.getEmail();
        Address updatedAddress = person.getAddress();
        Birthday updatedBirthday = person.getBirthday();
        Set<Tag> updatedTags = person.getTags();

        return new Person(updatedName, updatedPhone,
                updatedEmail, updatedAddress, updatedBirthday, remark, updatedTags);
    }

}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(new Remark(remark.get())) : Optional.empty();
    }
}
```
###### /java/seedu/address/model/person/Remark.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's remark in the address book.
 */
public class Remark {
    public final String remark;


    public Remark(String remark) {
        requireNonNull(remark);
        this.remark = remark.trim();
    }

    @Override
    public String toString() {
        return remark;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.remark.equals(((Remark) other).remark)); // state check
    }

    @Override
    public int hashCode() {
        return remark.hashCode();
    }
}
```
