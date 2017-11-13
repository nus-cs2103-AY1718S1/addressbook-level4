//@@author zenghou
package seedu.address.logic.commands;

import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;
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
import seedu.address.model.person.Facebook;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Adds a remark to a person by INDEX
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String COMMAND_ALIAS = "rmk";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the remark for a person specified in the INDEX.\n"
            + "Parameters: INDEX (must be a positive integer) " + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_REMARK + " Likes to drink coffee";

    public static final String MESSAGE_SUCCESS = "New remark added: %1$s";

    private final Index targetIndex;
    private Remark remark;

    public RemarkCommand(Index index, Remark remark) {
        this.targetIndex = index;
        this.remark = remark;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToAddRemarkTo = lastShownList.get(targetIndex.getZeroBased());
        ReadOnlyPerson personWithRemarkAdded = addRemarkToPerson(personToAddRemarkTo, remark);

        try {
            model.updatePerson(personToAddRemarkTo, personWithRemarkAdded);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, personWithRemarkAdded));

    }

    public Remark getRemark() {
        return remark;
    }

    public Index getIndex() {
        return targetIndex;
    }

    /**
     * Creates and returns a {@code Person} with the remark {@code rmk}
     */
    private static Person addRemarkToPerson (ReadOnlyPerson personToAddRemarkTo, Remark rmk) {
        assert personToAddRemarkTo != null;

        Name name = personToAddRemarkTo.getName();
        Phone phone = personToAddRemarkTo.getPhone();
        Email email = personToAddRemarkTo.getEmail();
        Address address = personToAddRemarkTo.getAddress();
        Birthday birthday = personToAddRemarkTo.getBirthday();
        Facebook facebook = personToAddRemarkTo.getFacebook();
        Set<Tag> tags = personToAddRemarkTo.getTags();

        Person personWithRemark = new Person(name, phone, email, address, birthday, facebook, tags);
        personWithRemark.setRemark(rmk);

        return personWithRemark;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof RemarkCommand) {
            RemarkCommand otherObject = (RemarkCommand) other;
            return this.getRemark().equals(otherObject.getRemark())
                    && this.getIndex().equals(otherObject.getIndex());
        }
        return false;
    }
}
