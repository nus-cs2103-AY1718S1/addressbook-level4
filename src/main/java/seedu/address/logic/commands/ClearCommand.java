package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.ListingUnit.MODULE;

import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.RemarkChangedEvent;
import seedu.address.commons.events.ui.ViewedLessonEvent;
import seedu.address.model.AddressBook;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.predicates.UniqueModuleCodePredicate;

//@@author junming403
/**
 * Clears the ModU data.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "ModU has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        Predicate predicate = new UniqueModuleCodePredicate(model.getUniqueCodeSet());
        ListingUnit.setCurrentListingUnit(MODULE);
        ListingUnit.setCurrentPredicate(predicate);
        model.unbookAllSlot();
        model.resetData(new AddressBook());
        model.updateFilteredLessonList(predicate);
        EventsCenter.getInstance().post(new ViewedLessonEvent());
        EventsCenter.getInstance().post(new RemarkChangedEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
