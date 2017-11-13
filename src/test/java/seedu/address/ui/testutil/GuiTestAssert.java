package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.MainWindow.DEFAULT_DP;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.GroupLabelHandle;
import guitests.guihandles.GroupListPanelHandle;
import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonDescriptionHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import javafx.scene.image.Image;
import seedu.address.commons.util.AppUtil;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {

    //@@author JunQuann
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertImageEquals(expectedCard.getDisplayPic(), actualCard.getDisplayPic());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson} with default Display Picture.
     */
    public static void assertCardDisplaysPerson(ReadOnlyPerson expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        Image expectedDisplayPic = getFileImage(expectedPerson);
        Image actualDisplayPic = actualCard.getDisplayPic();
        assertImageEquals(expectedDisplayPic, actualDisplayPic);
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    private static Image getFileImage(ReadOnlyPerson expectedPerson) {
        Image expectedDisplayPic;
        if (expectedPerson.getDisplayPic().getNewDisplayPicPath().equals(DEFAULT_DP)) {
            expectedDisplayPic = AppUtil.getImage(expectedPerson.getDisplayPic().getNewDisplayPicPath());
        } else {
            File expectedImgFile = new File(expectedPerson.getDisplayPic().getNewDisplayPicPath());
            expectedDisplayPic = new Image(expectedImgFile.toURI().toString());
        }
        return expectedDisplayPic;
    }

    /**
     * Asserts that {@code personDescription} displays the details of {@code expectedPerson}.
     */
    public static void assertDescriptionDisplaysPerson(ReadOnlyPerson expectedPerson,
                                                       PersonDescriptionHandle actualDescription) {

        Image expectedDisplayPic = getFileImage(expectedPerson);
        Image actualDisplayPic = actualDescription.getDisplayPic();
        assertImageEquals(expectedDisplayPic, actualDisplayPic);
        assertEquals(expectedPerson.getName().fullName, actualDescription.getName());
        assertEquals(expectedPerson.getPhone().value, actualDescription.getMobile());
        assertEquals(expectedPerson.getBirthday().value, actualDescription.getBirthday());
        assertEquals(expectedPerson.getEmail().value, actualDescription.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualDescription.getAddress());
        assertEquals(expectedPerson.getInstagramName().value, actualDescription.getInstagram());
        assertEquals(expectedPerson.getTwitterName().value, actualDescription.getTwitter());
    }
    //@@author

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, ReadOnlyPerson... persons) {
        for (int i = 0; i < persons.length; i++) {
            assertCardDisplaysPerson(persons[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<ReadOnlyPerson> persons) {
        assertListMatching(personListPanelHandle, persons.toArray(new ReadOnlyPerson[0]));
    }

    //@@author conantteo
    /**
     * Asserts that the list in {@code groupListPanelHandle} displays the name of the {@code tags} correctly.
     */
    public static void assertGroupListMatching(GroupListPanelHandle groupListPanelHandle, List<Tag> tags) {
        assertGroupListMatching(groupListPanelHandle, tags.toArray(new Tag[0]));
    }

    /**
     * Asserts that the list in {@code groupListPanelHandle} displays the name of the {@code tags} correctly.
     */
    public static void assertGroupListMatching(GroupListPanelHandle groupListPanelHandle, Tag... tags) {
        for (int i = 0; i < tags.length; i++) {
            assertGroupLabelDisplayTag(tags[i], groupListPanelHandle.getGroupLabelHandle(tags[i]));
        }
    }

    //@@ author
    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }

    //@@author conantteo
    public static void assertGroupLabelDisplayTag(Tag expectedTag, GroupLabelHandle groupLabel) {
        assertEquals(expectedTag.tagName, groupLabel.getGroupName());
    }

    //@@author JunQuann
    /**
     * Asserts that the image {@code expected} equals to {@code actual}
     */
    public static void assertImageEquals(Image expected, Image displayed) {
        boolean equal = true;
        for (int i = 0; i < expected.getWidth(); i++) {
            for (int j = 0; j < expected.getHeight(); j++) {
                if (expected.getPixelReader().getArgb(i, j) != displayed.getPixelReader().getArgb(i, j)) {
                    equal = false;
                }
            }
        }
        assertTrue(equal);
    }
}
