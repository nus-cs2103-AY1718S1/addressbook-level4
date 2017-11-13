package guitests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.person.SortCommand;
//@@author Alim95

public class SortMenuTest extends AddressBookGuiTest {

    private static final String ALICE = "Alice Pauline";
    private static final String BENSON = "Benson Meier";
    private static final String CARL = "Carl Kurz";
    private static final String DANIEL = "Daniel Meier";
    private static final String ELLE = "Elle Meyer";
    private static final String FIONA = "Fiona Kunz";
    private static final String GEORGE = "George Best";

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
        List<String> personListSortedByName = getPersonListSortedByName();
        for (int i = 0; i < 7; i++) {
            assertEquals(getPersonListPanel().getPersonCardHandle(i).getName(), personListSortedByName.get(i));
        }
    }

    /**
     * Asserts that sorting by phone is successful and the result display box is equals to {@code expectedMessage},
     * and the person list panel is updated accordingly
     */
    private void assertSortPhoneSuccess(String expectedMessage) {
        assertEquals(getResultDisplay().getText(), expectedMessage);
        List<String> personListSortedByPhone = getPersonListSortedByPhone();
        for (int i = 0; i < 7; i++) {
            assertEquals(getPersonListPanel().getPersonCardHandle(i).getName(), personListSortedByPhone.get(i));
        }
    }

    /**
     * Asserts that sorting by email is successful and the result display box is equals to {@code expectedMessage},
     * and the person list panel is updated accordingly
     */
    private void assertSortEmailSuccess(String expectedMessage) {
        assertEquals(getResultDisplay().getText(), expectedMessage);
        List<String> personListSortedByEmail = getPersonListSortedByEmail();
        for (int i = 0; i < 7; i++) {
            assertEquals(getPersonListPanel().getPersonCardHandle(i).getName(), personListSortedByEmail.get(i));
        }
    }

    /**
     * Asserts that sorting by address is successful and the result display box is equals to {@code expectedMessage},
     * and the person list panel is updated accordingly
     */
    private void assertSortAddressSuccess(String expectedMessage) {
        assertEquals(getResultDisplay().getText(), expectedMessage);
        List<String> personListSortedByAddress = getPersonListSortedByAddress();
        for (int i = 0; i < 7; i++) {
            assertEquals(getPersonListPanel().getPersonCardHandle(i).getName(), personListSortedByAddress.get(i));
        }
    }

    private List<String> getPersonListSortedByName() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    private List<String> getPersonListSortedByPhone() {
        return new ArrayList<>(Arrays.asList(ALICE, DANIEL, ELLE, FIONA, GEORGE, CARL, BENSON));
    }

    private List<String> getPersonListSortedByEmail() {
        return new ArrayList<>(Arrays.asList(ALICE, GEORGE, DANIEL, CARL, BENSON, FIONA, ELLE));
    }

    private List<String> getPersonListSortedByAddress() {
        return new ArrayList<>(Arrays.asList(DANIEL, ALICE, BENSON, GEORGE, FIONA, ELLE, CARL));
    }
}
