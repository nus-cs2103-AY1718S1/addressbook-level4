package seedu.address.logic.commands;

import static seedu.address.model.ListingUnit.ADDRESS;
import static seedu.address.model.ListingUnit.EMAIL;
import static seedu.address.model.ListingUnit.PERSON;
import static seedu.address.model.ListingUnit.PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeListingUnitEvent;
import seedu.address.model.ListingUnit;
import seedu.address.model.person.predicates.UniqueAddressPredicate;
import seedu.address.model.person.predicates.UniqueEmailPredicate;
import seedu.address.model.person.predicates.UniquePhonePredicate;


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
            return executeListByAttribute(addressPredicate);

        case ATTRIBUTE_EMAIL:
            ListingUnit.setCurrentListingUnit(EMAIL);
            UniqueEmailPredicate emailPredicate = new UniqueEmailPredicate(model.getUniqueEmailPersonSet());
            return executeListByAttribute(emailPredicate);

        case ATTRIBUTE_PHONE:
            ListingUnit.setCurrentListingUnit(PHONE);
            UniquePhonePredicate phonePredicate = new UniquePhonePredicate(model.getUniquePhonePersonSet());
            return executeListByAttribute(phonePredicate);

        default:
            ListingUnit.setCurrentListingUnit(PERSON);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_SUCCESS, attName));
        }
    }

    /**
     * execute the list command with different attributes.
     */
    private CommandResult executeListByAttribute(Predicate predicate) {
        model.updateFilteredPersonList(predicate);
        EventsCenter.getInstance().post(new ChangeListingUnitEvent());
        return new CommandResult(String.format(MESSAGE_SUCCESS, attName));
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
        if (attName != null && o.attName != null) {
            return attName.equals(o.attName);
        }

        return false;
    }
}
