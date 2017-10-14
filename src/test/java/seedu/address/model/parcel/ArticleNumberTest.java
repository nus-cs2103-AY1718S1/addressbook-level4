package seedu.address.model.parcel;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.parcel.ArticleNumber.MESSAGE_ARTICLE_NUMBER_CONSTRAINTS;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;

public class ArticleNumberTest {

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Test
    public void isValidArticleNumber() throws Exception {
        assertFalse(ArticleNumber.isValidArticleNumber("")); // empty string
        assertFalse(ArticleNumber.isValidArticleNumber(" ")); // spaces only

        // missing parts
        assertFalse(ArticleNumber.isValidArticleNumber("RR999999999")); // missing postfix 'SG'
        assertFalse(ArticleNumber.isValidArticleNumber("999999999SG")); // missing prefix 'RR'
        assertFalse(ArticleNumber.isValidArticleNumber("RRSG")); // missing digits
        assertFalse(ArticleNumber.isValidArticleNumber("999999999")); // missing postfix and prefix

        // invalid parts
        assertFalse(ArticleNumber.isValidArticleNumber("PE999999999SG")); // invalid prefix
        assertFalse(ArticleNumber.isValidArticleNumber("RR999999999TW")); // invalid postfix
        assertFalse(ArticleNumber.isValidArticleNumber("PE999999999TW")); // invalid prefix and postfix
        assertFalse(ArticleNumber.isValidArticleNumber("PE9999999999SG")); // too long
        assertFalse(ArticleNumber.isValidArticleNumber("RR99999999SG")); // too short
        assertFalse(ArticleNumber.isValidArticleNumber("RR999!@#999SG")); // contain non-digit symbols
        assertFalse(ArticleNumber.isValidArticleNumber("RR9999SG99999")); // wrong order
        assertFalse(ArticleNumber.isValidArticleNumber("SG999999999RR")); // wrong order

        // valid email
        assertTrue(ArticleNumber.isValidArticleNumber("RR999999999SG"));
        assertTrue(ArticleNumber.isValidArticleNumber("RR123456789SG"));
        assertTrue(ArticleNumber.isValidArticleNumber("RR001231230SG"));
    }

    @Test
    public void equals() throws Exception {
        ArticleNumber articleNumber = new ArticleNumber("RR001231230SG");
        ArticleNumber sameArticleNumber = new ArticleNumber("RR001231230SG");
        ArticleNumber differentArticleNumber = new ArticleNumber("RR999999999SG");

        assertFalse(differentArticleNumber.equals(articleNumber));
        assertFalse(articleNumber == null);

        assertEquals(articleNumber, sameArticleNumber);

        // check toString() equality
        assertFalse(articleNumber.toString().equals(differentArticleNumber.toString()));
        assertEquals(articleNumber.toString(), sameArticleNumber.toString());
        assertEquals(articleNumber.toString(), "RR001231230SG");

        // check hashCode() equality
        assertFalse(articleNumber.hashCode() == differentArticleNumber.hashCode());
        assertTrue(articleNumber.hashCode() == sameArticleNumber.hashCode());
    }

    @Test
    public void testInvalidArticleNumberInputThrowsExcpetion() throws IllegalValueException {
        expected.expect(IllegalValueException.class);
        expected.expectMessage(MESSAGE_ARTICLE_NUMBER_CONSTRAINTS);
        new ArticleNumber(" "); // illegal article number
    }

}
