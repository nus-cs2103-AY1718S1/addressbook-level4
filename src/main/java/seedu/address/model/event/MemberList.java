package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.EventOutputUtil;

import seedu.address.model.person.ReadOnlyPerson;


/**
 * A list of members of a given event that enforces uniqueness between its elements.
 * Accept an empty list
 * <p>
 * Supports minimal set of list operations for the app's features.
 *
 * @see ReadOnlyPerson#equals(Object)
 */
public class MemberList {

    private ArrayList<ReadOnlyPerson> members;

    public MemberList() {
        members = new ArrayList<>();
    }

    public MemberList(ArrayList<ReadOnlyPerson> members) {
        requireNonNull(members);
        CollectionUtil.elementsAreUnique(members);

        this.members = new ArrayList<>();
        this.members.addAll(members);
        this.members.sort((o1, o2) -> o1.getName().toString().compareToIgnoreCase(o2.getName().toString()));
    }

    /**
     * Returns a read-only list of member.
     *
     * @return an unmodifiable List of members
     */
    public List<ReadOnlyPerson> asReadOnlyMemberList() {
        ArrayList<ReadOnlyPerson> readOnlyList = new ArrayList<>();
        readOnlyList.addAll(members);

        return Collections.unmodifiableList(readOnlyList);
    }

    /**
     * Search member list to see if given person exist.
     *
     * @param toFind
     * @return returns true if success; false if not found
     */
    public boolean contains(ReadOnlyPerson toFind) {
        requireNonNull(toFind);
        return members.contains(toFind);
    }

    public Boolean isEmpty() {
        return members.isEmpty();
    }

    @Override
    public String toString() {
        return EventOutputUtil.toStringMembers(members);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemberList // instanceof handles nulls
                && this.members.equals(((MemberList) other).asReadOnlyMemberList())); // state check
    }

    @Override
    public int hashCode() {
        return members.hashCode();
    }


}
