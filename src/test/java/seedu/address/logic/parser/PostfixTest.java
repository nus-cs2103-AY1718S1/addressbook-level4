package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PostfixTest {

    @Test
    public void assertEqualsNotPostfixReturnsFalse() {
        assertFalse(new Postfix("abcd").equals(new Object()));
        assertFalse(new Postfix("abcd").equals(new Prefix("abcd")));
    }

    @Test
    public void assertEqualsSameInstanceReturnsTrue() {
        Postfix postfix = new Postfix("abcd");
        assertTrue(postfix.equals(postfix));
    }

    @Test
    public void assertEqualsSameStringValueDifferentInstanceReturnsTrue() {
        assertTrue(new Postfix("abcd").equals(new Postfix("abcd")));
    }

    @Test
    public void assertHashCodeNullStringReturnZero() {
        assertEquals(new Postfix(null).hashCode(), 0);
    }

    @Test
    public void assertHashCodeSameStringValueDifferentInstanceReturnsTrue() {
        assertTrue(new Postfix("abcd").hashCode() == new Postfix("abcd").hashCode());
    }
}
