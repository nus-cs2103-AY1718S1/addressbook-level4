package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
//@@author Alim95

public class SearchBoxTest extends AddressBookGuiTest {

    @Test
    public void useSearchBox() {

        /* Case: find "alice" in search box -> 1 person found */
        getSearchField().click();
        getSearchField().run("alice");
        assertEquals(mainWindowHandle.getPersonListPanel().getListSize(), 1);

        /* Case: find "AlIce" in search box -> 1 person found */
        getSearchField().click();
        getSearchField().run("AlIce");
        assertEquals(mainWindowHandle.getPersonListPanel().getListSize(), 1);

        /* Case: find "a" in search box -> 5 person found */
        getSearchField().click();
        getSearchField().run("a");
        assertEquals(mainWindowHandle.getPersonListPanel().getListSize(), 5);

        /* Case: find no person in search box -> displays full list */
        getSearchField().click();
        getSearchField().run("");
        assertEquals(mainWindowHandle.getPersonListPanel().getListSize(), 7);

        /* Case: find "Meier" in search box -> 2 person found */
        getSearchField().click();
        getSearchField().run("Meier");
        assertEquals(mainWindowHandle.getPersonListPanel().getListSize(), 2);
    }
}
