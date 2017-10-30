package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BENEFICIARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTRACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OWNER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREMIUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SIGNING_DATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.insurance.LifeInsurance;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author OscarWang114
/**
 * Creates an insurance relationship in the address book.
 */
public class AddLifeInsuranceCommand extends UndoableCommand {
    public static final String[] COMMAND_WORDS = {"addli", "ali", "+"};
    public static final String COMMAND_WORD = "addli";

    public static final String MESSAGE_USAGE = concatenateCommandWords(COMMAND_WORDS)
            + ": Creates an insurance relationship. "
            + "Parameters: "
            + PREFIX_OWNER + "OWNER "
            + PREFIX_INSURED + "INSURED "
            + PREFIX_BENEFICIARY + "BENEFICIARY "
            + PREFIX_CONTRACT + "CONTRACT"
            + PREFIX_PREMIUM + "PREMIUM "
            + PREFIX_SIGNING_DATE + "SIGNING_DATE "
            + PREFIX_EXPIRY_DATE + "EXPIRY_DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_OWNER + "OWNER "
            + PREFIX_INSURED + "INSURED "
            + PREFIX_BENEFICIARY + "BENEFICIARY "
            + PREFIX_CONTRACT + "CONTRACT"
            + PREFIX_PREMIUM + "PREMIUM "
            + PREFIX_SIGNING_DATE + "SIGNING_DATE "
            + PREFIX_EXPIRY_DATE + "EXPIRY_DATE";

    public static final String MESSAGE_SUCCESS = "New insurance added: %1$s";
    public static final String MESSAGE_DUPLICATE_INSURANCE = "This insurance already exists in the address book";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final String ownerName;
    private final String insuredName;
    private final String beneficiaryName;
    private final Double premium;
    private final String contractPath;
    private final String signingDate;
    private final String expiryDate;

    private ReadOnlyPerson personForOwner;
    private ReadOnlyPerson personForInsured;
    private ReadOnlyPerson personForBeneficiary;

    /**
     * Creates an AddLifeInsuranceCommand to add the specified {@code ReadOnlyInsurance}
     */
    public AddLifeInsuranceCommand(String ownerName, String insuredName, String beneficiaryName,
                                   Double premium, String contractPath, String signingDate, String expiryDate) {

        this.ownerName = ownerName;
        this.insuredName = insuredName;
        this.beneficiaryName = beneficiaryName;
        this.premium = premium;
        this.contractPath = contractPath;
        this.signingDate = signingDate;
        this.expiryDate = expiryDate;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (!arePersonsAllInList(lastShownList, ownerName, insuredName, beneficiaryName)) {
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND);
        }

        LifeInsurance lifeInsuranceToAdd = new LifeInsurance(personForOwner, personForInsured, personForBeneficiary,
                premium, contractPath, signingDate, expiryDate);

        try {
            model.updatePerson(personForOwner, new Person(personForOwner, lifeInsuranceToAdd));
            model.updatePerson(personForInsured, new Person(personForInsured, lifeInsuranceToAdd));
            model.updatePerson(personForBeneficiary, new Person(personForBeneficiary, lifeInsuranceToAdd));
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, lifeInsuranceToAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddLifeInsuranceCommand); // instanceof handles nulls
                //&& toAdd.equals(((AddLifeInsuranceCommand) other).toAdd));
        //TODO: need to compare every nonstatic class member.
    }

    /**
     * Check if all the Person parameters required to create an insurance are inside the list
     */
    public boolean arePersonsAllInList(List<ReadOnlyPerson> list, String owner, String insured, String beneficiary) {
        boolean ownerFlag = false;
        boolean insuredFlag = false;
        boolean beneficiaryFlag = false;

        for (ReadOnlyPerson person: list) {
            String personFullNameLowerCase = person.getName().toString().toLowerCase();
            if (personFullNameLowerCase.equals(owner.toLowerCase())) {
                ownerFlag = true;
                this.personForOwner = person;
            }
            if (personFullNameLowerCase.equals(insured.toLowerCase())) {
                insuredFlag = true;
                this.personForInsured = person;
            }
            if (personFullNameLowerCase.equals(beneficiary.toLowerCase())) {
                beneficiaryFlag = true;
                this.personForBeneficiary = person;
            }
            if (ownerFlag && beneficiaryFlag && insuredFlag) {
                return true;
            }
        }
        return false;
    }
}
