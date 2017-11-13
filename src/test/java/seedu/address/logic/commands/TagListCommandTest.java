package seedu.address.logic.commands;

//@@author wangyiming1019
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHRISTAG;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CHRIS;
import static seedu.address.testutil.TypicalPersons.CHRIS_WITH_NEW_TAG;
import static seedu.address.testutil.TypicalPersons.getTaglessAddressBook;

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

public class TagListCommandTest {
    private Model model;
    private Model expectedModel;
    private TagListCommand listTagsCommand;
    private String expectedMessage;

    @Before
    public void setUp() {
        model = new ModelManager(getTaglessAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        List<Tag> tagList = new ArrayList<Tag>();

        listTagsCommand = new TagListCommand();
        listTagsCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        StringBuilder expectedMessageString = new StringBuilder(listTagsCommand.MESSAGE_SUCCESS);
        for (ReadOnlyPerson p : model.getAddressBook().getPersonList()) {
            for (Tag tag : p.getTags()) {
                if (!tagList.contains(tag)) {
                    tagList.add(tag);
                    expectedMessageString.append("<").append(tag.getTagName()).append("> ");
                }
            }
        }
        expectedMessage = expectedMessageString.toString().trim();
    }

    @Test
    public void executeTagListNotEmpty() {
        assertCommandSuccess(listTagsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeEmptyTagListShowNothing() throws DuplicatePersonException {
        Model expectedModelWithNoTags = new ModelManager();
        String expectedMessageWithNoTags = listTagsCommand.MESSAGE_FAILURE;
        TagListCommand tagListCommandWithNoTags = new TagListCommand();
        tagListCommandWithNoTags.setData(expectedModelWithNoTags, new CommandHistory(),
                new UndoRedoStack());
        assertCommandSuccess(tagListCommandWithNoTags, expectedModelWithNoTags,
                expectedMessageWithNoTags, expectedModelWithNoTags);
    }

    @Test
    public void executeUpdateTagListWithPersonAdd() throws DuplicatePersonException {
        model.addPerson(CHRIS);
        expectedModel.addPerson(CHRIS);
        String newExpectedMessage = expectedMessage + " <" + VALID_TAG_CHRISTAG + ">";
        assertCommandSuccess(listTagsCommand, model, newExpectedMessage, expectedModel);
    }

    @Test
    public void executeUpdateTagListWithTagEdited() throws
            PersonNotFoundException, DuplicatePersonException {
        model.addPerson(CHRIS);
        expectedModel.addPerson(CHRIS);
        String newExpectedMessage = expectedMessage + " <" + VALID_TAG_CHRISTAG + ">";
        assertCommandSuccess(listTagsCommand, model, newExpectedMessage, expectedModel);
        model.updatePerson(CHRIS, CHRIS_WITH_NEW_TAG);
        expectedModel.updatePerson(CHRIS, CHRIS_WITH_NEW_TAG);
        assertCommandSuccess(listTagsCommand, model, expectedMessage, expectedModel);
    }
}
