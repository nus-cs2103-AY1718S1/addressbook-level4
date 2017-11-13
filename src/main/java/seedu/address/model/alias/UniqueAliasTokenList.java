package seedu.address.model.alias;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.alias.exceptions.DuplicateTokenKeywordException;
import seedu.address.model.alias.exceptions.TokenKeywordNotFoundException;

//@@author deep4k
/**
 * A list of alias that enforces no nulls and uniqueness between its elements.
 * <p>
 * Supports minimal set of list operation.
 *
 * @see AliasToken#equals(Object)
 */
public class UniqueAliasTokenList implements Iterable<AliasToken> {
    private final ObservableList<AliasToken> internalList = FXCollections.observableArrayList();
    private final ObservableList<ReadOnlyAliasToken> mappedList = EasyBind.map(internalList, (aliasToken)
        -> aliasToken);

    /**
     * Constructs empty AliasTokenList.
     */
    public UniqueAliasTokenList() {
    }

    /**
     * Creates a UniqueAliasTokenList using given AliasTokens.
     * Enforces no nulls
     */
    public UniqueAliasTokenList(Set<AliasToken> aliasTokens) {
        requireAllNonNull(aliasTokens);
        internalList.addAll(aliasTokens);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent aliasToken as the given argument.
     */
    public boolean contains(ReadOnlyAliasToken toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Returns true if a symbol with the specified tokenKeyword exists in the list
     *
     * @param tokenKeyword the tokenKeyword to check for
     * @return true if exists, false otherwise
     */
    public boolean contains(Keyword tokenKeyword) {
        for (AliasToken token : internalList) {
            if (token.getKeyword().equals(tokenKeyword)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Adds a AliasToken to the list.
     * An AliasToken is duplicate if another AliasTokens contains the same keyword.
     *
     * @throws DuplicateTokenKeywordException if the AliasToken to add is a duplicate.
     */
    public void add(ReadOnlyAliasToken toAdd) throws DuplicateTokenKeywordException {
        requireNonNull(toAdd);
        if (internalList.contains(toAdd.getKeyword())) {
            throw new DuplicateTokenKeywordException();
        }
        internalList.add(new AliasToken(toAdd));
    }

    /**
     * Removes the equivalent AliasToken from the list.
     *
     * @throws TokenKeywordNotFoundException if no such AliasToken could be found in the list.
     */
    public boolean remove(ReadOnlyAliasToken toRemove) throws TokenKeywordNotFoundException {
        requireNonNull(toRemove);
        if (!internalList.contains(toRemove)) {
            throw new TokenKeywordNotFoundException();
        }
        final boolean aliasFoundAndDeleted = internalList.remove(toRemove);
        return aliasFoundAndDeleted;
    }

    /**
     * Replaces the AliasToken {@code target} in the list with {@code newToken}.
     * newToken must have same keyword to target.
     *
     * @throws TokenKeywordNotFoundException if {@code target} could not be found in the list.
     */

    public void setAliasToken(ReadOnlyAliasToken target, ReadOnlyAliasToken newToken)
            throws DuplicateTokenKeywordException, TokenKeywordNotFoundException {
        requireAllNonNull(target, newToken);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TokenKeywordNotFoundException();
        }

        assert target.getKeyword().equals(newToken.getKeyword());
        internalList.set(index, new AliasToken(newToken));
    }

    /**
     * Clears this list and copies all elements from the replacement list to this.
     *
     * @param replacement the other list
     */
    public void setAliasTokens(UniqueAliasTokenList replacement) {
        requireAllNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void setAliasTokens(List<? extends ReadOnlyAliasToken> aliasTokens) throws DuplicateTokenKeywordException {
        final UniqueAliasTokenList replacement = new UniqueAliasTokenList();
        for (final ReadOnlyAliasToken aliasToken : aliasTokens) {
            replacement.add(new AliasToken(aliasToken));
        }
        setAliasTokens(replacement);
    }

    public int size() {
        return internalList.size();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyAliasToken> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<AliasToken> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAliasTokenList // instanceof handles nulls
                && this.internalList.equals(((UniqueAliasTokenList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
