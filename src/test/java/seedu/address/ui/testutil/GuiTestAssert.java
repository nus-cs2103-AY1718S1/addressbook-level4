package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.PlaceCardHandle;
import guitests.guihandles.PlaceListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.place.ReadOnlyPlace;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PlaceCardHandle expectedCard, PlaceCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getWebsite(), actualCard.getWebsite());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPlace}.
     */
    public static void assertCardDisplaysPlace(ReadOnlyPlace expectedPlace, PlaceCardHandle actualCard) {
        assertEquals(expectedPlace.getName().fullName, actualCard.getName());
        assertEquals(expectedPlace.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPlace.getWebsite().value, actualCard.getWebsite());
        assertEquals(expectedPlace.getAddress().value, actualCard.getAddress());
        assertEquals(expectedPlace.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code placeListPanelHandle} displays the details of {@code places} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PlaceListPanelHandle placeListPanelHandle, ReadOnlyPlace... places) {
        for (int i = 0; i < places.length; i++) {
            assertCardDisplaysPlace(places[i], placeListPanelHandle.getPlaceCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code placeListPanelHandle} displays the details of {@code places} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PlaceListPanelHandle placeListPanelHandle, List<ReadOnlyPlace> places) {
        assertListMatching(placeListPanelHandle, places.toArray(new ReadOnlyPlace[0]));
    }

    /**
     * Asserts the size of the list in {@code placeListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PlaceListPanelHandle placeListPanelHandle, int size) {
        int numberOfPlaces = placeListPanelHandle.getListSize();
        assertEquals(size, numberOfPlaces);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
