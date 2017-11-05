package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showSomePersonsOnly;
import java.util.Comparator;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

public class SortCommandTestUtil {
    private static final int FILTER_NUM_PERSONS_TO_SHOW = 3;

    public static void assertUnfilteredSortCommandSuccess(SortCommand sortCommand, Model model,
                                                          Comparator<ReadOnlyPerson> expectedSortComparator,
                                                          String expectedMessage) {
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.sortPersons(expectedSortComparator);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    public static void assertFilteredSortCommandSuccess(SortCommand sortCommand, Model model,
                                                        Comparator<ReadOnlyPerson> expectedSortComparator,
                                                        String expectedMessage) {
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showSomePersonsOnly(model, FILTER_NUM_PERSONS_TO_SHOW);
        showSomePersonsOnly(expectedModel, FILTER_NUM_PERSONS_TO_SHOW);
        expectedModel.sortPersons(expectedSortComparator);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }
}
