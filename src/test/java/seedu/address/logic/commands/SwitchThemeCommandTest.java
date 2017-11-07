package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_THEME;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author zhangshuoyang
public class SwitchThemeCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {

        SwitchThemeCommand stc = new SwitchThemeCommand(INDEX_FIRST_THEME);
        Index indexOne = Index.fromOneBased(1);
        Index indexTwo = Index.fromOneBased(2);

        // same object -> returns true
        assertTrue(stc.equals(stc));

        // same value -> returns true
        assertTrue(stc.equals(new SwitchThemeCommand(indexOne)));

        // different type -> returns false
        assertFalse(stc.equals(1));

        // different value -> returns false
        assertFalse(stc.equals(new SwitchThemeCommand(indexTwo)));

        // null -> returns false
        assertFalse(stc == (null));

    }


}
