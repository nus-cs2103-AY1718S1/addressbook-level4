package seedu.address.model.social;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.DuplicateDataException;

//@@author marvinchin
/**
 * Contains unit tests for {@code UniqueSocialInfoList}.
 */
public class UniqueSocialInfoListTest {
    private static final SocialInfo ALICE_FACEBOOK = new SocialInfo("facebook", "alice", "facebook.com/alice");
    private static final SocialInfo ALICE_TWITTER = new SocialInfo("twitter", "alice", "instagram.com/alice");
    private static final SocialInfo BOB_FACEBOOK = new SocialInfo("facebook", "bob", "facebook.com/bob");
    private static final SocialInfo BOB_TWITTER = new SocialInfo("twitter", "bob", "instagram.com/bob");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void uniqueSocialInfoList_toSet_success() throws DuplicateDataException {
        UniqueSocialInfoList uniqueSocialInfoList = prepareUniqueSocialInfoList(ALICE_FACEBOOK, ALICE_TWITTER);
        HashSet<SocialInfo> expectedSet = new HashSet<>(Arrays.asList(ALICE_FACEBOOK, ALICE_TWITTER));
        assertEquals(expectedSet, uniqueSocialInfoList.toSet());
    }

    @Test
    public void uniqueSocialInfoList_addUnique_success() throws DuplicateDataException {
        UniqueSocialInfoList uniqueSocialInfoList = prepareUniqueSocialInfoList(ALICE_FACEBOOK);
        uniqueSocialInfoList.add(ALICE_TWITTER);
        HashSet<SocialInfo> expectedSet = new HashSet<>(Arrays.asList(ALICE_FACEBOOK, ALICE_TWITTER));
        assertEquals(expectedSet, uniqueSocialInfoList.toSet());
    }

    @Test
    public void uniqueSocialInfoList_addDuplicateSocialType_throwsDuplicateSocialTypeException()
            throws DuplicateDataException {
        UniqueSocialInfoList uniqueSocialInfoList = prepareUniqueSocialInfoList(ALICE_FACEBOOK);
        thrown.expect(UniqueSocialInfoList.DuplicateSocialTypeException.class);
        uniqueSocialInfoList.add(BOB_FACEBOOK);
    }

    @Test
    public void uniqueSocialInfoList_setSocialInfos_success() {
        // Should work for an empty list
        UniqueSocialInfoList uniqueSocialInfoList = new UniqueSocialInfoList();
        HashSet<SocialInfo> toSet = new HashSet<>(Arrays.asList(BOB_FACEBOOK, BOB_TWITTER));
        uniqueSocialInfoList.setSocialInfos(toSet);
        assertEquals(toSet, uniqueSocialInfoList.toSet());
        // Should work for list with existing social infos
        HashSet<SocialInfo> nextToSet = new HashSet<>(Arrays.asList(ALICE_FACEBOOK, ALICE_TWITTER));
        uniqueSocialInfoList.setSocialInfos(nextToSet);
        assertEquals(nextToSet, uniqueSocialInfoList.toSet());
    }

    @Test
    public void uniqueSocialInfoList_equals_success() throws DuplicateDataException {
        UniqueSocialInfoList aliceList = prepareUniqueSocialInfoList(ALICE_FACEBOOK, ALICE_TWITTER);
        UniqueSocialInfoList bobList = prepareUniqueSocialInfoList(BOB_FACEBOOK, BOB_TWITTER);
        assertFalse(aliceList.equals(bobList));
        UniqueSocialInfoList aliceListCopy = prepareUniqueSocialInfoList(ALICE_FACEBOOK, ALICE_TWITTER);
        assertTrue(aliceList.equals(aliceListCopy));

        UniqueSocialInfoList aliceListOrdered = new UniqueSocialInfoList();
        aliceListOrdered.add(ALICE_FACEBOOK);
        aliceListOrdered.add(ALICE_TWITTER);
        UniqueSocialInfoList aliceListReversed = new UniqueSocialInfoList();
        aliceListReversed.add(ALICE_TWITTER);
        aliceListReversed.add(ALICE_FACEBOOK);
        assertFalse(aliceListOrdered.equals(aliceListReversed));
    }

    @Test
    public void uniqueSocialInfoList_equalsOrderInsensitive_success()
            throws DuplicateDataException {
        UniqueSocialInfoList aliceList = prepareUniqueSocialInfoList(ALICE_FACEBOOK, ALICE_TWITTER);
        UniqueSocialInfoList bobList = prepareUniqueSocialInfoList(BOB_FACEBOOK, BOB_TWITTER);
        assertFalse(aliceList.equalsOrderInsensitive(bobList));
        UniqueSocialInfoList aliceListCopy = prepareUniqueSocialInfoList(ALICE_FACEBOOK, ALICE_TWITTER);
        assertTrue(aliceList.equalsOrderInsensitive(aliceListCopy));

        UniqueSocialInfoList aliceListOrdered = new UniqueSocialInfoList();
        aliceListOrdered.add(ALICE_FACEBOOK);
        aliceListOrdered.add(ALICE_TWITTER);
        UniqueSocialInfoList aliceListReversed = new UniqueSocialInfoList();
        aliceListReversed.add(ALICE_TWITTER);
        aliceListReversed.add(ALICE_FACEBOOK);
        assertTrue(aliceListOrdered.equalsOrderInsensitive(aliceListReversed));
    }

    private static UniqueSocialInfoList prepareUniqueSocialInfoList(SocialInfo... socialInfos)
            throws DuplicateDataException {
        return new UniqueSocialInfoList(
                new HashSet<>(Arrays.asList(socialInfos))
        );
    }
}
