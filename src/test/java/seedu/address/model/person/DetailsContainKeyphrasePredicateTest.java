//@@author zenghou
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class DetailsContainKeyphrasePredicateTest {
    private ReadOnlyPerson samplePerson = new PersonBuilder().withName("Peter Lim")
            .withPhone("99887766")
            .withEmail("peterlim@gmail.com")
            .withAddress("Orchard Towers Block 39 #13-12")
            .withFacebook("21")
            .withTags("business", "professional")
            .withRemark("met during a networking event")
            .build();

    @Test
    public void equals() {
        String keyphraseOne = "this is the first sample keyphrase";
        String keyphraseTwo = "this is the second sample keyphrase";

        DetailsContainKeyphrasePredicate firstPredicate = new DetailsContainKeyphrasePredicate(keyphraseOne);
        DetailsContainKeyphrasePredicate secondPredicate = new DetailsContainKeyphrasePredicate(keyphraseTwo);

        // same object
        assertTrue(firstPredicate.equals(firstPredicate));
        assertTrue(secondPredicate.equals(secondPredicate));
        assertTrue(firstPredicate.equals(new DetailsContainKeyphrasePredicate("this is the first sample keyphrase")));

        // different object
        assertFalse(firstPredicate.equals(secondPredicate));
        assertFalse(secondPredicate.equals(firstPredicate));

        // different type
        assertFalse(firstPredicate.equals(1));
    }

    @Test
    public void testDetailsDoNotContainKeyphrase_returnsFalse() {
        // keyphrase (more than 1 word) neither matches details partially or in full
        DetailsContainKeyphrasePredicate predicate = new DetailsContainKeyphrasePredicate("pineapple banana");
        assertFalse(predicate.test(samplePerson));

        // keyphrase (1 word with extra alphabet) neither matches details partially or in full
        DetailsContainKeyphrasePredicate predicateTwo = new DetailsContainKeyphrasePredicate("professionals");
        assertFalse(predicateTwo.test(samplePerson));

        // keyphrase (2 word with extra alphabet) neither matches details partially or in full
        DetailsContainKeyphrasePredicate predicateThree = new DetailsContainKeyphrasePredicate("Peter Lim Tan");
        assertFalse(predicateThree.test(samplePerson));
    }

    @Test
    public void testDetailsContainKeyphrase_returnsTrue() {
        // matches Name in full
        DetailsContainKeyphrasePredicate predicateNameFull = new DetailsContainKeyphrasePredicate("Peter Lim");
        assertTrue(predicateNameFull.test(samplePerson));

        // matches Name partially
        DetailsContainKeyphrasePredicate predicateNamePartial = new DetailsContainKeyphrasePredicate("ter Li");
        assertTrue(predicateNamePartial.test(samplePerson));

        // matches Name partially
        DetailsContainKeyphrasePredicate predicateNameDifferentCase = new DetailsContainKeyphrasePredicate("ER Li");
        assertTrue(predicateNameDifferentCase.test(samplePerson));

        // matches Phone in full
        DetailsContainKeyphrasePredicate predicatePhoneFull = new DetailsContainKeyphrasePredicate("99887766");
        assertTrue(predicatePhoneFull.test(samplePerson));

        // matches Phone partially
        DetailsContainKeyphrasePredicate predicatePhonePartial = new DetailsContainKeyphrasePredicate("8877");
        assertTrue(predicatePhonePartial.test(samplePerson));

        // matches Email in full
        DetailsContainKeyphrasePredicate predicateEmailFull = new DetailsContainKeyphrasePredicate("peterlim@gmail."
                + "com");
        assertTrue(predicateEmailFull.test(samplePerson));

        // matches Email partially
        DetailsContainKeyphrasePredicate predicateEmailPartially = new DetailsContainKeyphrasePredicate("gmail.com");
        assertTrue(predicateEmailPartially.test(samplePerson));

        // matches Email different case
        DetailsContainKeyphrasePredicate predicateEmailDifferentCase = new DetailsContainKeyphrasePredicate("GMAIL."
                + "com");
        assertTrue(predicateEmailDifferentCase.test(samplePerson));

        // matches Address in full
        DetailsContainKeyphrasePredicate predicateAddressFull = new DetailsContainKeyphrasePredicate("Orchard Towers "
                + "Block 39 #13-12");
        assertTrue(predicateAddressFull.test(samplePerson));

        // matches Address partially
        DetailsContainKeyphrasePredicate predicateAddressPartially = new DetailsContainKeyphrasePredicate("39 #13-12");
        assertTrue(predicateAddressPartially.test(samplePerson));

        // matches Address in full, different case
        DetailsContainKeyphrasePredicate predicateAddressDifferentCase = new DetailsContainKeyphrasePredicate("Orchard "
                + "Towers Block 39 #13-12");
        assertTrue(predicateAddressDifferentCase.test(samplePerson));

        // matches Facebook in full
        DetailsContainKeyphrasePredicate predicateFacebookFull = new DetailsContainKeyphrasePredicate("21");
        assertTrue(predicateAddressFull.test(samplePerson));

        // matches Facebook partially
        DetailsContainKeyphrasePredicate predicateFacebookPartially = new DetailsContainKeyphrasePredicate("1");
        assertTrue(predicateAddressPartially.test(samplePerson));

        // matches Tag in full
        DetailsContainKeyphrasePredicate predicateTagFull = new DetailsContainKeyphrasePredicate("professional");
        assertTrue(predicateTagFull.test(samplePerson));

        // matches Tag partially
        DetailsContainKeyphrasePredicate predicateTagPartial = new DetailsContainKeyphrasePredicate("ssional");
        assertTrue(predicateTagPartial.test(samplePerson));

        // matches Tag in full, different case
        DetailsContainKeyphrasePredicate predicateTagFullDifferentCase = new DetailsContainKeyphrasePredicate("pro"
                + "FeSsIoNal");
        assertTrue(predicateTagFullDifferentCase.test(samplePerson));

        // matches Remark in full
        DetailsContainKeyphrasePredicate predicateRemarkFull = new DetailsContainKeyphrasePredicate("met during a "
                + "networking event");
        assertTrue(predicateRemarkFull.test(samplePerson));

        // matches Remark partially
        DetailsContainKeyphrasePredicate predicateRemarkPartial = new DetailsContainKeyphrasePredicate("networking"
                + " event");
        assertTrue(predicateRemarkPartial.test(samplePerson));

        // matches Remark in full, different Case
        DetailsContainKeyphrasePredicate predicateRemarkFullDifferentCase = new DetailsContainKeyphrasePredicate("met"
                + " during a nEtWorKing EVent");
        assertTrue(predicateRemarkFull.test(samplePerson));
    }
}
