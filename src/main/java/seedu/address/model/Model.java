package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.insurance.ReadOnlyInsurance;
import seedu.address.model.insurance.exceptions.DuplicateContractFileNameException;
import seedu.address.model.insurance.exceptions.DuplicateInsuranceException;
import seedu.address.model.insurance.exceptions.InsuranceNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyInsurance> PREDICATE_SHOW_ALL_INSURANCES = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Adds the given insurance */
    void addLifeInsurance(ReadOnlyInsurance insurance)
            throws DuplicateInsuranceException, DuplicateContractFileNameException;

    /**
     * Replaces the given life insurance {@code target} with {@code editedLifeInsurance}.
     *
     * @throws InsuranceNotFoundException if {@code target} could not be found in the map.
     */
    void updateLifeInsurance(ReadOnlyInsurance target, ReadOnlyInsurance editedPerson)
            throws InsuranceNotFoundException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    //@@author OscarWang114
    /** Returns an unmodifiable view of the filtered insurance list */
    ObservableList<ReadOnlyInsurance> getFilteredInsuranceList();

    /**
     * Updates the filter of the filtered insurance list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredInsuranceList(Predicate<ReadOnlyInsurance> predicate);
    //@@author

    void deleteInsurance(ReadOnlyInsurance insuranceToDelete) throws InsuranceNotFoundException;
}
