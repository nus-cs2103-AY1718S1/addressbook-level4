package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CommentTest {

    @Test
    public void equals() {
        Comment comment = new Comment("Hello");

        // same object -> returns true
        assertTrue(comment.equals(comment));

        // same values -> returns true
        Comment commentCopy = new Comment(comment.value);
        assertTrue(comment.equals(commentCopy));

        // different types -> returns false
        assertFalse(comment.equals(1));

        // null -> returns false
        assertFalse(comment.equals(null));

        // different person -> returns false
        Comment differentComment = new Comment("Bye");
        assertFalse(comment.equals(differentComment));
    }
}
