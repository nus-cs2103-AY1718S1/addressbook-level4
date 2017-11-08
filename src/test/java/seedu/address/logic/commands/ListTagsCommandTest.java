//@@author PokkaKiyo
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UNIQUETAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UNIQUETAG2;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CORNIE;
import static seedu.address.testutil.TypicalPersons.CORNIE_NEW_NON_UNIQUE_TAG;
import static seedu.address.testutil.TypicalPersons.CORNIE_NEW_UNIQUE_TAG;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

public class ListTagsCommandTest {
    private Model model;
    private Model expectedModel;
    private Model modelWithNoTags;
    private ListTagsCommand listTagsCommand;
    private List<Tag> listOfAllTags;
    private String expectedMessage;
    private String expectedMessageForNoTags;


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        listOfAllTags = new ArrayList<Tag>();

        listTagsCommand = new ListTagsCommand();
        listTagsCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        //creating the expected message for all tags in typical addressbook
        StringBuilder expectedMessageInStringBuilder = new StringBuilder(listTagsCommand.MESSAGE_SUCCESS);
        for (ReadOnlyPerson p : model.getAddressBook().getPersonList()) {
            for (Tag tag : p.getTags()) {
                if (!listOfAllTags.contains(tag)) {
                    listOfAllTags.add(tag);
                    expectedMessageInStringBuilder.append("[").append(tag.getTagName()).append("] ");
                }
            }
        }
        expectedMessage = expectedMessageInStringBuilder.toString().trim();

    }

    @Test
    public void execute_showsCorrectTagListWhenThereAreContactsWithTags() {
        assertCommandSuccess(listTagsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addNewPersonWithNewTag_showsCorrectTagList() throws DuplicatePersonException {
        model.addPerson(CORNIE);
        expectedModel.addPerson(CORNIE);
        String newExpectedMessage = expectedMessage + " [" + VALID_TAG_UNIQUETAG + "]";

        assertCommandSuccess(listTagsCommand, model, newExpectedMessage, expectedModel);
    }

    @Test
    public void execute_removePersonWithParticularUniqueTag_showsCorrectTagList() throws
            PersonNotFoundException, DuplicatePersonException {
        model.addPerson(CORNIE);
        expectedModel.addPerson(CORNIE);
        String newExpectedMessage = expectedMessage + " [" + VALID_TAG_UNIQUETAG + "]";

        assertCommandSuccess(listTagsCommand, model, newExpectedMessage, expectedModel);


        model.deletePerson(CORNIE);
        expectedModel.deletePerson(CORNIE);

        assertCommandSuccess(listTagsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editPersonWithParticularTag_showsCorrectTagList() throws
            PersonNotFoundException, DuplicatePersonException {
        model.addPerson(CORNIE);
        expectedModel.addPerson(CORNIE);
        String newExpectedMessage = expectedMessage + " [" + VALID_TAG_UNIQUETAG + "]";

        assertCommandSuccess(listTagsCommand, model, newExpectedMessage, expectedModel);

        //editing unique tag to another unique tag
        model.updatePerson(CORNIE, CORNIE_NEW_UNIQUE_TAG);
        expectedModel.updatePerson(CORNIE, CORNIE_NEW_UNIQUE_TAG);
        String newExpectedMessage2 = expectedMessage + " [" + VALID_TAG_UNIQUETAG2 + "]";

        assertCommandSuccess(listTagsCommand, model, newExpectedMessage2, expectedModel);

        //editing unique tag to non-unique tag
        model.updatePerson(CORNIE_NEW_UNIQUE_TAG, CORNIE_NEW_NON_UNIQUE_TAG);
        expectedModel.updatePerson(CORNIE_NEW_UNIQUE_TAG, CORNIE_NEW_NON_UNIQUE_TAG);

        assertCommandSuccess(listTagsCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_noTagsShowsEmptyTagList() throws DuplicatePersonException {
        modelWithNoTags = new ModelManager();
        expectedMessageForNoTags = listTagsCommand.MESSAGE_FAILURE;
        ListTagsCommand listTagsCommandForNoTags = new ListTagsCommand();
        listTagsCommandForNoTags.setData(modelWithNoTags, new CommandHistory(), new UndoRedoStack());

        assertCommandSuccess(listTagsCommandForNoTags, modelWithNoTags, expectedMessageForNoTags, modelWithNoTags);

    }

}
