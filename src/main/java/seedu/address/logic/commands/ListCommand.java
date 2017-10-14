package seedu.address.logic.commands;


import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeListingUnitEvent;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.predicates.UniqueLocationPredicate;
import seedu.address.model.module.predicates.UniqueModuleCodePredicate;

import static seedu.address.model.ListingUnit.LOCATION;
import static seedu.address.model.ListingUnit.MODULE;


/**
 * Lists unique locations or unique module codes of all lessons according to user's specification.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all locations or all module codes and "
            + "displays them as a list with index numbers.\n"
            + "Parameters: module/location\n"
            + "Example: " + COMMAND_WORD + " module";

    public static final String MESSAGE_SUCCESS = "Listed all %1$s";

    public static final String MODULE_KEYWORD = "modules";
    public static final String LOCATION_KEYWORD = "locations";

    private final String parameter;

    public ListCommand(String attributeName) {
        this.parameter = attributeName;
    }

    @Override
    public CommandResult execute() {

        if (parameter.equals(MODULE_KEYWORD)) {
            ListingUnit.setCurrentListingUnit(MODULE);
            UniqueModuleCodePredicate codePredicate = new UniqueModuleCodePredicate(model.getUniqueCodeSet());
            return executeListByAttribute(codePredicate);
        } else if (parameter.equals(LOCATION_KEYWORD)) {
            ListingUnit.setCurrentListingUnit(LOCATION);
            UniqueLocationPredicate locationPredicate = new UniqueLocationPredicate(model.getUniqueLocationSet());
            return executeListByAttribute(locationPredicate);
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
        EventsCenter.getInstance().post(new ChangeListingUnitEvent());
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
