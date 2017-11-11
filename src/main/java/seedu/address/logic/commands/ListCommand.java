package seedu.address.logic.commands;

import static seedu.address.model.ListingUnit.LESSON;
import static seedu.address.model.ListingUnit.LOCATION;
import static seedu.address.model.ListingUnit.MODULE;

import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.RefreshPanelEvent;
import seedu.address.commons.events.ui.ViewedLessonEvent;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.predicates.MarkedListPredicate;
import seedu.address.model.module.predicates.UniqueLocationPredicate;
import seedu.address.model.module.predicates.UniqueModuleCodePredicate;

//@@author junming403
/**
 * Lists unique locations or unique module codes of all lessons according to user's specification.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all locations or all module codes and "
            + "displays them as a list with index numbers.\n"
            + "Parameters: module/location/marked\n"
            + "Example: " + COMMAND_WORD + " module";

    public static final String MESSAGE_SUCCESS = "Listed %1$s(s)";

    public static final String MODULE_KEYWORD = "module";
    public static final String LOCATION_KEYWORD = "location";
    public static final String MARKED_LIST_KEYWORD = "marked";

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private final String parameter;


    public ListCommand(String attributeName) {
        this.parameter = attributeName;
    }

    @Override
    public CommandResult execute() {

        switch (parameter) {
        case MODULE_KEYWORD :
            logger.info("---[List success]Switching Listing element to " + MODULE_KEYWORD);
            return executeListModule();
        case LOCATION_KEYWORD:
            logger.info("---[List success]Switching Listing element to " + LOCATION_KEYWORD);
            return executeListLocation();
        case MARKED_LIST_KEYWORD:
            logger.info("---[List success]Switching to marked lesson list");
            return executeListMarked();
        default:
            assert false : "There cannot be other parameters passed in";
            return null;
        }
    }

    /**
     * execute list by module.
     */
    private CommandResult executeListModule() {
        ListingUnit.setCurrentListingUnit(MODULE);
        UniqueModuleCodePredicate codePredicate = new UniqueModuleCodePredicate(model.getUniqueCodeSet());
        EventsCenter.getInstance().post(new ViewedLessonEvent());
        ListingUnit.setCurrentPredicate(codePredicate);
        return executeListByAttribute(codePredicate);
    }

    /**
     * execute list by location.
     */
    private CommandResult executeListLocation() {
        ListingUnit.setCurrentListingUnit(LOCATION);
        UniqueLocationPredicate locationPredicate = new UniqueLocationPredicate(model.getUniqueLocationSet());
        EventsCenter.getInstance().post(new ViewedLessonEvent());
        ListingUnit.setCurrentPredicate(locationPredicate);
        return executeListByAttribute(locationPredicate);
    }

    /**
     * execute list all marked lessons.
     */
    private CommandResult executeListMarked() {
        ListingUnit.setCurrentListingUnit(LESSON);
        MarkedListPredicate markedListPredicate = new MarkedListPredicate();
        model.setViewingPanelAttribute(MARKED_LIST_KEYWORD);
        ListingUnit.setCurrentPredicate(markedListPredicate);
        EventsCenter.getInstance().post(new ViewedLessonEvent());
        return executeListByAttribute(markedListPredicate);
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
