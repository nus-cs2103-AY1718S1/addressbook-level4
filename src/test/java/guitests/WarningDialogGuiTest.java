// package guitests;
//
// import guitests.guihandles.AlertDialogHandle;
// import seedu.address.commons.core.index.Index;
// import seedu.address.commons.events.logic.ContactDeletionEvent;
//
// import org.junit.Test;
//
// import static org.junit.Assert.assertEquals;
// import static seedu.address.testutil.EventsUtil.postLater;
// import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
// import static seedu.address.ui.UiManager.DELETE_WARNING_DIALOG_CONTENT_MESSAGE;
// import static seedu.address.ui.UiManager.DELETE_WARNING_DIALOG_HEADER_MESSAGE;
// import static seedu.address.ui.UiManager.DELETE_WARNING_DIALOG_STAGE_TITLE;
//
// public class WarningDialogGuiTest extends AddressBookGuiTest {
//
// private static final Index index1 = INDEX_FIRST_PERSON;
//
// @Test
// public void showWarningDialogs() {
// postLater(new ContactDeletionEvent(index1));
//
// guiRobot.waitForEvent(() -> guiRobot.isWindowShown(DELETE_WARNING_DIALOG_STAGE_TITLE));
//
// AlertDialogHandle alertDialog = new AlertDialogHandle(guiRobot.getStage(DELETE_WARNING_DIALOG_STAGE_TITLE));
// assertEquals(DELETE_WARNING_DIALOG_HEADER_MESSAGE, alertDialog.getHeaderText());
// assertEquals(DELETE_WARNING_DIALOG_CONTENT_MESSAGE, alertDialog.getContentText());
// }
//
// }
