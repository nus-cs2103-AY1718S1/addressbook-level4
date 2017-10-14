package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ListingUnit;
import seedu.address.model.Model;
import seedu.address.model.module.*;
import seedu.address.model.module.exceptions.LessonNotFoundException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.predicates.UniqueAddressPredicate;
import seedu.address.model.person.predicates.UniqueEmailPredicate;
import seedu.address.model.person.predicates.UniquePhonePredicate;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODULES;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the lesson(s) by module(if current listing element is module).\n"
            + ": Deletes the lesson(s) by location(if current listing element is location).\n"
            + ": Deletes the lesson(s) identified by the index (if current listing element is lesson).\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_LESSON_SUCCESS = "Deleted Lesson: %1$s";
    public static final String MESSAGE_DELETE_LESSON_WITH_LOCATION_SUCCESS = "Deleted location: %1$s";
    public static final String MESSAGE_DELETE_PERSON_WITH_MODULE_SUCCESS = "Deleted Module: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyModule> lastShownList = model.getFilteredModuleList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        switch (ListingUnit.getCurrentListingUnit()) {

        case LESSON:
            // to be implement

        case LOCATION:
            return deleteLessonWithSpecifiedLocation();

        default:
            ReadOnlyModule moduleToDelete = lastShownList.get(targetIndex.getZeroBased());
            try {
                model.deleteModule(moduleToDelete);
            } catch (ModuleNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }
            return new CommandResult(String.format(MESSAGE_DELETE_LESSON_SUCCESS, moduleToDelete));
        }
    }

    /**
     * Delete all modules with specified location
     */
    private CommandResult deleteLessonWithSpecifiedLocation(Location location) {

        model.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        ObservableList<ReadOnlyModule> moduleList = model.getFilteredModuleList();

        try {
            UniqueLessonList lessonList;
            for (ReadOnlyModule p : moduleList) {
                lessonList = p.getUniqueLessonList();

                for (ReadOnlyLesson l : lessonList) {
                    if (l.getLocation().equals(location)) {
                        lessonList.remove(l);
                    }
                }
            }
            model.updateFilteredModuleList(new UniqueAddressPredicate(model.getUniqueAdPersonSet()));

        } catch (LessonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }
        return new CommandResult(String.format(MESSAGE_DELETE_LESSON_WITH_LOCATION_SUCCESS, location));
    }

    /**
     * Delete all persons with specified email.
     */
    private CommandResult deletePersonWithSpecifiedEmail(Email email) {

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        ObservableList<ReadOnlyPerson> personList = model.getFilteredPersonList();
        List<ReadOnlyPerson> personsToDelete = new ArrayList<ReadOnlyPerson>();

        try {
            for (ReadOnlyPerson p : personList) {
                if (p.getEmail().equals(email)) {
                    personsToDelete.add(p);
                }
            }
            model.deletePersonSet(personsToDelete);
            model.updateFilteredPersonList(new UniqueEmailPredicate(model.getUniqueEmailPersonSet()));

        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_WITH_EMAIL_SUCCESS, email));
    }

    /**
     * Delete all persons with specified phone.
     */
    private CommandResult deletePersonWithSpecifiedPhone(Phone phone) {

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        ObservableList<ReadOnlyPerson> personList = model.getFilteredPersonList();
        List<ReadOnlyPerson> personsToDelete = new ArrayList<ReadOnlyPerson>();

        try {
            for (ReadOnlyPerson p : personList) {
                if (p.getPhone().equals(phone)) {
                    personsToDelete.add(p);
                }
            }
            model.deletePersonSet(personsToDelete);
            model.updateFilteredPersonList(new UniquePhonePredicate(model.getUniquePhonePersonSet()));

        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_WITH_PHONE_SUCCESS, phone));
    }



    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
