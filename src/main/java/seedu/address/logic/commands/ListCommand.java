package seedu.address.logic.commands;

import static seedu.address.model.ListingUnit.LESSON;
import static seedu.address.model.ListingUnit.LOCATION;
import static seedu.address.model.ListingUnit.MODULE;

import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.RefreshPanelEvent;
import seedu.address.commons.events.ui.ViewedLessonEvent;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.predicates.MarkedListPredicate;
import seedu.address.model.module.predicates.UniqueLocationPredicate;
import seedu.address.model.module.predicates.UniqueModuleCodePredicate;

/**
 * Lists unique locations or unique module codes of all lessons according to user's specification.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all locations or all module codes and "
            + "displays them as a list with index numbers.\n"
            + "Parameters: module/location/favouriteList\n"
            + "Example: " + COMMAND_WORD + " module";

    public static final String MESSAGE_SUCCESS = "Listed %1$s(s)";

    public static final String MODULE_KEYWORD = "module";
    public static final String LOCATION_KEYWORD = "location";
    public static final String MARKED_LIST_KEYWORD = "marked";

    private final String parameter;

    public ListCommand(String attributeName) {
        this.parameter = attributeName;
    }

    @Override
    public CommandResult execute() {

        if (parameter.equals(MODULE_KEYWORD)) {
            ListingUnit.setCurrentListingUnit(MODULE);
            UniqueModuleCodePredicate codePredicate = new UniqueModuleCodePredicate(model.getUniqueCodeSet());
            EventsCenter.getInstance().post(new ViewedLessonEvent());
            ListingUnit.setCurrentPredicate(codePredicate);
            return executeListByAttribute(codePredicate);
        } else if (parameter.equals(LOCATION_KEYWORD)) {
            ListingUnit.setCurrentListingUnit(LOCATION);
            UniqueLocationPredicate locationPredicate = new UniqueLocationPredicate(model.getUniqueLocationSet());
            EventsCenter.getInstance().post(new ViewedLessonEvent());
            ListingUnit.setCurrentPredicate(locationPredicate);
            return executeListByAttribute(locationPredicate);
        } else if (parameter.equals(MARKED_LIST_KEYWORD)) {
            ListingUnit.setCurrentListingUnit(LESSON);
            MarkedListPredicate favouriteListPredicate = new MarkedListPredicate();
            ListingUnit.setCurrentPredicate(favouriteListPredicate);
            EventsCenter.getInstance().post(new ViewedLessonEvent());
            return executeListByAttribute(favouriteListPredicate);
        } else {
            assert false : "There cannot be other parameters passed in";
            return null;
        }
    }

    /**
     * execute the list command with different attributes.
     */
    private CommandResult executeListByAttribute(Predicate predicate) {
        model.updateFilteredLessonList(predicate);
        if (predicate instanceof MarkedListPredicate) {
            EventsCenter.getInstance().post(new ViewedLessonEvent());
        }
        EventsCenter.getInstance().post(new RefreshPanelEvent());
        return new CommandResult(String.format(MESSAGE_SUCCESS, parameter));
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ListCommand)) {
            return false;
        }

        if (other == this) {
            return true;
        }

        ListCommand o = (ListCommand) other;
        if (parameter != null && o.parameter != null) {
            return parameter.equals(o.parameter);
        }

        return false;
    }
}
