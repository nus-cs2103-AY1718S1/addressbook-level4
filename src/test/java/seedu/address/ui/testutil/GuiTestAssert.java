package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.ParcelCardHandle;
import guitests.guihandles.ParcelListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.parcel.ReadOnlyParcel;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(ParcelCardHandle expectedCard, ParcelCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedParcel}.
     */
    public static void assertCardDisplaysParcel(ReadOnlyParcel expectedParcel, ParcelCardHandle actualCard) {
        assertEquals(expectedParcel.getName().fullName, actualCard.getName());
        assertEquals(expectedParcel.getPhone().value, actualCard.getPhone());
        assertEquals(expectedParcel.getEmail().value, actualCard.getEmail());
        assertEquals(expectedParcel.getAddress().toString(), actualCard.getAddress());
        assertEquals(expectedParcel.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code parcelListPanelHandle} displays the details of {@code parcels} correctly and
     * in the correct order.
     */
    public static void assertListMatching(ParcelListPanelHandle parcelListPanelHandle, ReadOnlyParcel... parcels) {
        for (int i = 0; i < parcels.length; i++) {
            assertCardDisplaysParcel(parcels[i], parcelListPanelHandle.getParcelCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code parcelListPanelHandle} displays the details of {@code parcels} correctly and
     * in the correct order.
     */
    public static void assertListMatching(ParcelListPanelHandle parcelListPanelHandle, List<ReadOnlyParcel> parcels) {
        assertListMatching(parcelListPanelHandle, parcels.toArray(new ReadOnlyParcel[0]));
    }

    /**
     * Asserts the size of the list in {@code parcelListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(ParcelListPanelHandle parcelListPanelHandle, int size) {
        int numberOfPeople = parcelListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
