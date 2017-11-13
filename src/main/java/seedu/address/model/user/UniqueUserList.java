package seedu.address.model.user;

//@@author quanle1994

import static java.util.Objects.requireNonNull;

import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.user.exceptions.DuplicateUserException;
import seedu.address.model.user.exceptions.UserNotFoundException;

//@@author quanle1994

/**
 * A list of users that enforces uniqueness between its elements and does not allow nulls.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see User#equals(Object)
 */
public class UniqueUserList {
    private final ObservableList<User> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyUser> mappedList = EasyBind.map(internalList, (user) -> user);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReadOnlyUser toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicateUserException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(ReadOnlyUser toAdd) throws DuplicateUserException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateUserException();
        }
        internalList.add(new User(toAdd));
    }

    /**
     * Removes the equivalent event from the list.
     *
     * @throws UserNotFoundException if no such event could be found in the list.
     */
    public boolean remove(ReadOnlyUser toRemove) throws UserNotFoundException {
        requireNonNull(toRemove);
        final boolean userFoundAndDeleted = internalList.remove(toRemove);
        if (!userFoundAndDeleted) {
            throw new UserNotFoundException();
        }
        return userFoundAndDeleted;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyUser> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    public void setUsers(List<? extends ReadOnlyUser> users) throws DuplicateUserException {
        final UniqueUserList replacement = new UniqueUserList();
        for (final ReadOnlyUser user : users) {
            replacement.add(new User(user));
        }
        setUsers(replacement);
    }

    public void setUsers(UniqueUserList replacement) throws DuplicateUserException {
        this.internalList.setAll(replacement.internalList);
    }


    public User getUser(String userName) throws UserNotFoundException {
        int targetIndex = this.internalList.indexOf(new User(userName));
        if (targetIndex < 0) {
            throw new UserNotFoundException();
        }
        return this.internalList.get(targetIndex);
    }

    public String getSalt(String userId) throws UserNotFoundException {
        return getUser(userId).getSalt();
    }
}
