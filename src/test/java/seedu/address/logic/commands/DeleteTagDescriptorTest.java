package seedu.address.logic.commands;
//@@author pjunwei95
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.Test;

import seedu.address.logic.commands.DeleteTagCommand.DeleteTagDescriptor;
import seedu.address.testutil.DeleteTagDescriptorBuilder;

public class DeleteTagDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        DeleteTagDescriptor descriptorWithSameValues = new DeleteTagDescriptor(TAG_DESC_AMY);
        assertTrue(TAG_DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(TAG_DESC_AMY.equals(TAG_DESC_AMY));

        // null -> returns false
        assertFalse(TAG_DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(TAG_DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(TAG_DESC_AMY.equals(TAG_DESC_BOB));

        // different tags -> returns false
        DeleteTagDescriptor editedAmy = new DeleteTagDescriptorBuilder(TAG_DESC_AMY)
                .withTags(VALID_TAG_HUSBAND).build();
        assertFalse(TAG_DESC_AMY.equals(editedAmy));
    }
}
