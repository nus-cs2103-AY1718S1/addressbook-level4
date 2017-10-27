package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.group.ReadOnlyGroup;
import seedu.address.model.group.exceptions.DuplicateGroupException;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalGroups {

    public static final ReadOnlyGroup ALICE_GROUP = new GroupBuilder().withName("Alice Pauline Group").build();
    public static final ReadOnlyGroup BENSON_GROUP = new GroupBuilder().withName("Benson Meier Group").build();
    public static final ReadOnlyGroup CARL_GROUP = new GroupBuilder().withName("Carl Kurz Group").build();
    public static final ReadOnlyGroup DANIEL_GROUP = new GroupBuilder().withName("Daniel Meier Group").build();
    public static final ReadOnlyGroup ELLE_GROUP = new GroupBuilder().withName("Elle Meyer Group").build();
    public static final ReadOnlyGroup FIONA_GROUP = new GroupBuilder().withName("Fiona Kunz Group").build();
    public static final ReadOnlyGroup GEORGE_GROUP = new GroupBuilder().withName("George Best Group").build();

    // Manually added
    public static final ReadOnlyGroup HOON_GROUP = new GroupBuilder().withName("Hoon Meier Group").build();
    public static final ReadOnlyGroup IDA_GROUP = new GroupBuilder().withName("Ida Mueller Group").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyGroup AMY_GROUP = new GroupBuilder().withName(VALID_NAME_AMY).build();
    public static final ReadOnlyGroup BOB_GROUP = new GroupBuilder().withName(VALID_NAME_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier Group"; // A keyword that matches MEIER

    private TypicalGroups() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical groups.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyGroup group : getTypicalGroups()) {
            try {
                ab.addGroup(group);
            } catch (DuplicateGroupException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyGroup> getTypicalGroups() {
        return new ArrayList<>(Arrays.asList(
                ALICE_GROUP, BENSON_GROUP, CARL_GROUP, DANIEL_GROUP, ELLE_GROUP, FIONA_GROUP, GEORGE_GROUP));
    }

    public static AddressBook getEmptyAddressBook() {
        AddressBook ab = new AddressBook();
        return ab;
    }

    public static AddressBook getSortedAddressBook(String type, boolean isReverseOrder) {
        AddressBook ab = new AddressBook();
        List<ReadOnlyGroup> groupList;

        switch(type) {
        case "name":
            groupList = getTypicalGroups();
            break;
        default:
            groupList = getTypicalGroups();
        }

        if (isReverseOrder) {
            Collections.reverse(groupList);
        }

        for (ReadOnlyGroup group : groupList) {
            try {
                ab.addGroup(group);
            } catch (DuplicateGroupException e) {
                assert false : "not possible";
            }
        }

        return ab;
    }
}
