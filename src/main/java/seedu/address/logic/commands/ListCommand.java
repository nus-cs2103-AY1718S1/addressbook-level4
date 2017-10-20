package seedu.address.logic.commands;

import static seedu.address.model.ListingUnit.LESSON;
import static seedu.address.model.ListingUnit.LOCATION;
import static seedu.address.model.ListingUnit.MODULE;

import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeListingUnitEvent;

import seedu.address.model.ListingUnit;
<<<<<<< HEAD
import seedu.address.model.module.predicates.FavouriteListPredicate;
import seedu.address.model.module.predicates.UniqueLocationPredicate;
import seedu.address.model.module.predicates.UniqueModuleCodePredicate;


=======
import seedu.address.model.person.predicates.FavourListPredicate;
import seedu.address.model.person.predicates.UniqueAddressPredicate;
import seedu.address.model.person.predicates.UniqueEmailPredicate;
import seedu.address.model.person.predicates.UniquePhonePredicate;
>>>>>>> master


/**
 * Lists unique locations or unique module codes of all lessons according to user's specification.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all locations or all module codes and "
            + "displays them as a list with index numbers.\n"
<<<<<<< HEAD
            + "Parameters: module/location/favouriteList\n"
            + "Example: " + COMMAND_WORD + " module";

    public static final String MESSAGE_SUCCESS = "Listed all %1$s";

    public static final String MODULE_KEYWORD = "module";
    public static final String LOCATION_KEYWORD = "location";
    public static final String FAVOURITE_LIST_KEYWORD = "favouriteList";

    private final String parameter;
=======
            + "Parameters: address/email/phone/collection\n"
            + "Example: " + COMMAND_WORD + " address";

    public static final String MESSAGE_SUCCESS = "Listed all %1$s";

    public static final String DEFAULT_LISTING_ELEMENT = "Persons";
    public static final String ATTRIBUTE_ADDRESS = "address";
    public static final String ATTRIBUTE_COLLECTION = "collection";
    public static final String ATTRIBUTE_EMAIL = "email";
    public static final String ATTRIBUTE_PHONE = "phone";


    private final String attName;
>>>>>>> master

    public ListCommand(String attributeName) {
        this.parameter = attributeName;
    }

    @Override
    public CommandResult execute() {

<<<<<<< HEAD
        if (parameter.equals(MODULE_KEYWORD)) {
            ListingUnit.setCurrentListingUnit(MODULE);
            UniqueModuleCodePredicate codePredicate = new UniqueModuleCodePredicate(model.getUniqueCodeSet());
            return executeListByAttribute(codePredicate);
        } else if (parameter.equals(LOCATION_KEYWORD)) {
            ListingUnit.setCurrentListingUnit(LOCATION);
            UniqueLocationPredicate locationPredicate = new UniqueLocationPredicate(model.getUniqueLocationSet());
            return executeListByAttribute(locationPredicate);
        } else if (parameter.equals(FAVOURITE_LIST_KEYWORD)) {
            ListingUnit.setCurrentListingUnit(LESSON);
            FavouriteListPredicate favouriteListPredicate = model.getFavouriteListPredicate();
            return executeListByAttribute(favouriteListPredicate);
        } else {
            assert false : "There cannot be other parameters passed in";
            return null;
=======
        switch (attName) {

        case ATTRIBUTE_ADDRESS:
            ListingUnit.setCurrentListingUnit(ADDRESS);
            UniqueAddressPredicate addressPredicate = new UniqueAddressPredicate(model.getUniqueAdPersonSet());
            return executeListByAttribute(addressPredicate);

        case ATTRIBUTE_EMAIL:
            ListingUnit.setCurrentListingUnit(EMAIL);
            UniqueEmailPredicate emailPredicate = new UniqueEmailPredicate(model.getUniqueEmailPersonSet());
            return executeListByAttribute(emailPredicate);

        case ATTRIBUTE_PHONE:
            ListingUnit.setCurrentListingUnit(PHONE);
            UniquePhonePredicate phonePredicate = new UniquePhonePredicate(model.getUniquePhonePersonSet());
            return executeListByAttribute(phonePredicate);

        case ATTRIBUTE_COLLECTION:
            ListingUnit.setCurrentListingUnit(PERSON);
            FavourListPredicate favourListPredicate = model.getFavourListPredicate();
            return executeListByAttribute(favourListPredicate);

        default:
            ListingUnit.setCurrentListingUnit(PERSON);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_SUCCESS, attName));
>>>>>>> master
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
