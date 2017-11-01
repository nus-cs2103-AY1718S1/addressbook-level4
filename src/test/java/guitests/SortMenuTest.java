package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.logic.commands.person.SortCommand;
//@@author Alim95

public class SortMenuTest extends AddressBookGuiTest {

    @Test
    public void useSortMenu() {

        /* Case: sort by name -> list has been sorted alphabetically by name */
        getSortMenu().click();
        getSortMenu().run("name");
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "name");
        assertSortNameSuccess(expectedMessage);

        /* Case: sort by phone -> list has been sorted numerically by phone number */
        getSortMenu().click();
        getSortMenu().run("phone");
        expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "phone");
        assertSortPhoneSuccess(expectedMessage);

        /* Case: sort by email -> list has been sorted alphabetically by email */
        getSortMenu().click();
        getSortMenu().run("email");
        expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "email");
        assertSortEmailSuccess(expectedMessage);

        /* Case: sort by address -> list has been sorted alphabetically by address */
        getSortMenu().click();
        getSortMenu().run("address");
        expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "address");
        assertSortAddressSuccess(expectedMessage);
    }

    /**
     * Asserts that sorting by name is successful and the result display box is equals to {@code expectedMessage},
     * and the person list panel is updated accordingly
     */
    private void assertSortNameSuccess(String expectedMessage) {
        assertEquals(getResultDisplay().getText(), expectedMessage);
        assertEquals(getPersonListPanel().getPersonCardHandle(0).getName(), "Alice Pauline");
        assertEquals(getPersonListPanel().getPersonCardHandle(1).getName(), "Benson Meier");
        assertEquals(getPersonListPanel().getPersonCardHandle(2).getName(), "Carl Kurz");
        assertEquals(getPersonListPanel().getPersonCardHandle(3).getName(), "Daniel Meier");
        assertEquals(getPersonListPanel().getPersonCardHandle(4).getName(), "Elle Meyer");
        assertEquals(getPersonListPanel().getPersonCardHandle(5).getName(), "Fiona Kunz");
        assertEquals(getPersonListPanel().getPersonCardHandle(6).getName(), "George Best");
    }

    /**
     * Asserts that sorting by phone is successful and the result display box is equals to {@code expectedMessage},
     * and the person list panel is updated accordingly
     */
    private void assertSortPhoneSuccess(String expectedMessage) {
        assertEquals(getResultDisplay().getText(), expectedMessage);
        assertEquals(getPersonListPanel().getPersonCardHandle(0).getName(), "Alice Pauline");
        assertEquals(getPersonListPanel().getPersonCardHandle(1).getName(), "Daniel Meier");
        assertEquals(getPersonListPanel().getPersonCardHandle(2).getName(), "Elle Meyer");
        assertEquals(getPersonListPanel().getPersonCardHandle(3).getName(), "Fiona Kunz");
        assertEquals(getPersonListPanel().getPersonCardHandle(4).getName(), "George Best");
        assertEquals(getPersonListPanel().getPersonCardHandle(5).getName(), "Carl Kurz");
        assertEquals(getPersonListPanel().getPersonCardHandle(6).getName(), "Benson Meier");
    }

    /**
     * Asserts that sorting by email is successful and the result display box is equals to {@code expectedMessage},
     * and the person list panel is updated accordingly
     */
    private void assertSortEmailSuccess(String expectedMessage) {
        assertEquals(getResultDisplay().getText(), expectedMessage);
        assertEquals(getPersonListPanel().getPersonCardHandle(0).getName(), "Alice Pauline");
        assertEquals(getPersonListPanel().getPersonCardHandle(1).getName(), "George Best");
        assertEquals(getPersonListPanel().getPersonCardHandle(2).getName(), "Daniel Meier");
        assertEquals(getPersonListPanel().getPersonCardHandle(3).getName(), "Carl Kurz");
        assertEquals(getPersonListPanel().getPersonCardHandle(4).getName(), "Benson Meier");
        assertEquals(getPersonListPanel().getPersonCardHandle(5).getName(), "Fiona Kunz");
        assertEquals(getPersonListPanel().getPersonCardHandle(6).getName(), "Elle Meyer");
    }

    /**
     * Asserts that sorting by address is successful and the result display box is equals to {@code expectedMessage},
     * and the person list panel is updated accordingly
     */
    private void assertSortAddressSuccess(String expectedMessage) {
        assertEquals(getResultDisplay().getText(), expectedMessage);
        assertEquals(getPersonListPanel().getPersonCardHandle(0).getName(), "Daniel Meier");
        assertEquals(getPersonListPanel().getPersonCardHandle(1).getName(), "Alice Pauline");
        assertEquals(getPersonListPanel().getPersonCardHandle(2).getName(), "Benson Meier");
        assertEquals(getPersonListPanel().getPersonCardHandle(3).getName(), "George Best");
        assertEquals(getPersonListPanel().getPersonCardHandle(4).getName(), "Fiona Kunz");
        assertEquals(getPersonListPanel().getPersonCardHandle(5).getName(), "Elle Meyer");
        assertEquals(getPersonListPanel().getPersonCardHandle(6).getName(), "Carl Kurz");
    }
}
