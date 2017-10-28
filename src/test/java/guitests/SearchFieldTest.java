package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SearchFieldTest extends AddressBookGuiTest {

    @Test
    public void useSearchField() {

        /* Case: find "alice" in search field -> 1 person found */
        getSearchField().click();
        getSearchField().run("alice");
        assertEquals(mainWindowHandle.getPersonListPanel().getListSize(), 1);

        /* Case: find "AlIce" in search field -> 1 person found */
        getSearchField().click();
        getSearchField().run("AlIce");
        assertEquals(mainWindowHandle.getPersonListPanel().getListSize(), 1);

        /* Case: find "a" in search field -> 5 person found */
        getSearchField().click();
        getSearchField().run("a");
        assertEquals(mainWindowHandle.getPersonListPanel().getListSize(), 5);

        /* Case: find no person in search field -> displays full list */
        getSearchField().click();
        getSearchField().run("");
        assertEquals(mainWindowHandle.getPersonListPanel().getListSize(), 7);

        /* Case: find "Meier" in search field -> 2 person found */
        getSearchField().click();
        getSearchField().run("Meier");
        assertEquals(mainWindowHandle.getPersonListPanel().getListSize(), 2);
    }
}
