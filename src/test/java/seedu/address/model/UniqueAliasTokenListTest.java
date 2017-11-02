package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.alias.UniqueAliasTokenList;

//@@author deep4k
public class UniqueAliasTokenListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueAliasTokenList uniqueAliasTokenList = new UniqueAliasTokenList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueAliasTokenList.asObservableList().remove(0);
    }
}
