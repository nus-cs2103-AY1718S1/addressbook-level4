package seedu.address.logic.commands;

import static seedu.address.model.ListingUnit.ADDRESS;
import static seedu.address.model.ListingUnit.EMAIL;
import static seedu.address.model.ListingUnit.PERSON;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeListingUnitEvent;
import seedu.address.model.ListingUnit;
import seedu.address.model.person.UniqueAddressPredicate;
import seedu.address.model.person.UniqueEmailPredicate;
import seedu.address.model.person.UniquePhonePredicate;

import javax.sql.rowset.Predicate;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all persons or all attributes(if specified) and "
            + "displays them as a list with index numbers.\n"
            + "Parameters: address/email/phone\n"
            + "Example: " + COMMAND_WORD + " address";

    public static final String MESSAGE_SUCCESS = "Listed all %1$s";

    public static final String DEFAULT_LISTING_ELEMENT = "Persons";
    public static final String ATTRIBUTE_ADDRESS = "address";
    public static final String ATTRIBUTE_EMAIL = "email";
    public static final String ATTRIBUTE_PHONE = "phone";

    private final String attName;

    public ListCommand(String attributeName) {
        this.attName = attributeName;
    }

    public ListCommand() {
        this.attName = DEFAULT_LISTING_ELEMENT;
    }

    private boolean hasAttribute() {
        return !attName.equals(DEFAULT_LISTING_ELEMENT);
    }

    @Override
    public CommandResult execute() {

        switch (attName) {

            case ATTRIBUTE_ADDRESS:
                ListingUnit.setCurrentListingUnit(ADDRESS);
                UniqueAddressPredicate addressPredicate = new UniqueAddressPredicate(model.getUniqueAdPersonSet());
                model.updateFilteredPersonList(addressPredicate);
                EventsCenter.getInstance().post(new ChangeListingUnitEvent());
                return new CommandResult(String.format(MESSAGE_SUCCESS, attName));

            case ATTRIBUTE_EMAIL:
                ListingUnit.setCurrentListingUnit(EMAIL);
                UniqueEmailPredicate emailPredicate = new UniqueEmailPredicate(model.getUniqueEmailPersonSet());
                model.updateFilteredPersonList(emailPredicate);
                EventsCenter.getInstance().post(new ChangeListingUnitEvent());

            case ATTRIBUTE_PHONE:
                ListingUnit.setCurrentListingUnit(EMAIL);
                UniquePhonePredicate phonePredicate = new UniquePhonePredicate(model.getUniquePhonePersonSet());
                model.updateFilteredPersonList(phonePredicate);
                EventsCenter.getInstance().post(new ChangeListingUnitEvent());

            default:
                ListingUnit.setCurrentListingUnit(PERSON);
                model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
                return new CommandResult(String.format(MESSAGE_SUCCESS, attName));
        }
    }
}
