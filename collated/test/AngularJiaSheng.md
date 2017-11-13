# AngularJiaSheng
###### /java/seedu/address/model/UniqueWebLinkListTest.java
``` java
public class UniqueWebLinkListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueWebLinkList uniqueTagList = new UniqueWebLinkList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTagList.asObservableList().remove(0);
    }
}
```
###### /java/systemtests/DeleteTagCommandSystemTest.java
``` java

public class DeleteTagCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void deleteTag() throws Exception {
        /* Case: delete 1 valid tag from all person in address book.
         * -> success
         */
        Model expectedModel = getModel();
        Tag tagForDelete = new Tag(VALID_TAG_FRIEND);
        String command = DeleteTagCommand.COMMAND_WORD + " " + VALID_TAG_FRIEND;
        assertCommandSuccess(command, tagForDelete);

        /* Case: Undo previous command
         * -> success
         */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete a non existent tag.
         * -> fail: MESSAGE_TAG_NOT_FOUND displayed.
         */
        tagForDelete = new Tag("nonExistentTag");
        command = DeleteTagCommand.COMMAND_WORD + " nonExistentTag";
        expectedResultMessage = DeleteTagCommand.MESSAGE_TAG_NOT_FOUND;
        assertCommandFailure(command, expectedResultMessage);
    }


    /**
     * Executes the {@code TagDeleteCommand}
     * that deletes the tag {@code tagForDelete} from all person in the model.
     * Asserts that the message expected is displayed to user.
     */
    private void assertCommandSuccess(String command, Tag tagForDelete) {
        Model expectedModel = getModel();
        try {
            expectedModel.deleteTag(tagForDelete);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("Not suppposed to have duplicate");
        } catch (PersonNotFoundException pnf) {
            throw new IllegalArgumentException("All person should be found");
        }
        String expectedResultMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_ALL_TAG_SUCCESS, tagForDelete);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Executes the {@code TagDeleteCommand}
     * that deletes the tag {@code tagForDelete} from all person in the model.
     * Asserts that the message expected is displayed to user.
     * Assets the command box displays an empty string.
     * Asserts default style is show by the command box.
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes the {@code TagDeleteCommand} and verifies that the command box displays {@code command},
     * the result display box displays {@code expectedResultMessage} and
     * the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged,
     * and the command box has the error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
    }
}
```
