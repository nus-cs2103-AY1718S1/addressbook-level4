//@@author cqhchan
package seedu.address.model.account;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.account.exceptions.DuplicateAccountException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 *
 */
public class UniqueAccountList implements Iterable<Account> {

    private final ObservableList<Account> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyAccount> mappedList = EasyBind.map(internalList, (account) -> account);

    /**
     * Returns true if the list contains an equivalent account as the given argument.
     */
    public boolean contains(ReadOnlyAccount toCheck) {
        requireNonNull(toCheck);

        for (Account account : internalList) {

            if (account.getUsername().fullName.equals(toCheck.getUsername().fullName) && account.getPassword().value.equals(toCheck.getPassword().value)) {
                return true;
            }

        }
        return false;
    }
    /**
     * Adds a account to the list.
     *
     * @throws DuplicatePersonException if the account to add is a duplicate of an existing account in the list.
     */
    public void add(ReadOnlyAccount toAdd) throws DuplicateAccountException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAccountException();
        }
        internalList.add(new Account(toAdd));
    }

    /**
     * Replaces the account {@code target} in the list with {@code editedAccount}.
     *
     * @throws DuplicatePersonException if the replacement is equivalent to another existing account in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    public void setAccount(ReadOnlyAccount target, ReadOnlyAccount editedAccount)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedAccount);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.equals(editedAccount) && internalList.contains(editedAccount)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, new Account(editedAccount));
    }

    /**
     * Removes the equivalent account from the list.
     *
     * @throws PersonNotFoundException if no such account could be found in the list.
     */
    public boolean remove(ReadOnlyAccount toRemove) throws PersonNotFoundException {
        requireNonNull(toRemove);
        final boolean accountFoundAndDeleted = internalList.remove(toRemove);
        if (!accountFoundAndDeleted) {
            throw new PersonNotFoundException();
        }
        return accountFoundAndDeleted;
    }

    public void setAccounts(UniqueAccountList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setAccounts(List<? extends ReadOnlyAccount> accounts) throws DuplicateAccountException {
        final UniqueAccountList replacement = new UniqueAccountList();
        for (final ReadOnlyAccount account : accounts) {
            replacement.add(new Account(account));
        }
        setAccounts(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyAccount> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<Account> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAccountList // instanceof handles nulls
                && this.internalList.equals(((UniqueAccountList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
