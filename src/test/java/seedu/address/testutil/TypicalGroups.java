package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.group.ReadOnlyGroup;
import seedu.address.model.group.exceptions.DuplicateGroupException;

//@@author eldonng
/**
 * A utility class containing a list of {@code Group} objects to be used in tests.
 */
public class TypicalGroups {

    public static final ReadOnlyGroup SAMPLE_GROUP_1 = new GroupBuilder().withGroupName("Sample Group 1")
            .withGroupMembers(new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL))).build();

    public static final ReadOnlyGroup SAMPLE_GROUP_2 = new GroupBuilder().withGroupName("Sample Group 2")
            .withGroupMembers(new ArrayList<>(Arrays.asList(DANIEL, ELLE, FIONA))).build();

    public static final ReadOnlyGroup SAMPLE_GROUP_3 = new GroupBuilder().withGroupName("Sample Group 3")
            .withGroupMembers(new ArrayList<>(Arrays.asList(ALICE, GEORGE))).build();

    /**
     * Adds all the groups to the sample address book
     * @param ab
     */
    public static void addGroupsToAddressBook(AddressBook ab) {
        for (ReadOnlyGroup group : TypicalGroups.getTypicalGroups()) {
            try {
                ab.addGroup(group);
            } catch (DuplicateGroupException e) {
                assert false : "not possible";
            }
        }
    }

    public static List<ReadOnlyGroup> getTypicalGroups() {
        return new ArrayList<>(Arrays.asList(SAMPLE_GROUP_1, SAMPLE_GROUP_2, SAMPLE_GROUP_3));
    }
}
