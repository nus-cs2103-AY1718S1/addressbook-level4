package seedu.address.model.social;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UniqueSocialInfoListTest {
    private static MockSocialInfo aliceFacebook = new MockSocialInfo("alice", "FACEBOOK");
    private static MockSocialInfo aliceTwitter = new MockSocialInfo("alice", "TWITTER");
    private static MockSocialInfo bobFacebook = new MockSocialInfo("bob", "FACEBOOK");
    private static MockSocialInfo bobTwitter = new MockSocialInfo("bob", "TWITTER");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void uniqueSocialInfoList_toSet_success() {
        UniqueSocialInfoList uniqueSocialInfoList = prepareUniqueSocialInfoList(aliceFacebook, aliceTwitter);
        HashSet<SocialInfo> expectedSet = new HashSet<>(Arrays.asList(aliceFacebook, aliceTwitter));
        assertEquals(expectedSet, uniqueSocialInfoList.toSet());
    }

    @Test
    public void uniqueSocialInfoList_addUnique_success() throws UniqueSocialInfoList.DuplicateSocialTypeException {
        UniqueSocialInfoList uniqueSocialInfoList = prepareUniqueSocialInfoList(aliceFacebook);
        uniqueSocialInfoList.add(aliceTwitter);
        HashSet<SocialInfo> expectedSet = new HashSet<>(Arrays.asList(aliceFacebook, aliceTwitter));
        assertEquals(expectedSet, uniqueSocialInfoList.toSet());
    }

    @Test
    public void uniqueSocialInfoList_addDuplicateSocialType_throwsDuplicateSocialTypeException() throws UniqueSocialInfoList.DuplicateSocialTypeException {
        UniqueSocialInfoList uniqueSocialInfoList = prepareUniqueSocialInfoList(aliceFacebook);
        thrown.expect(UniqueSocialInfoList.DuplicateSocialTypeException.class);
        uniqueSocialInfoList.add(bobFacebook);
    }

    @Test
    public void uniqueSocialInfoList_setSocialInfos_success() {
        // Should work for an empty list
        UniqueSocialInfoList uniqueSocialInfoList = new UniqueSocialInfoList();
        HashSet<SocialInfo> toSet = new HashSet<>(Arrays.asList(bobFacebook, bobTwitter));
        uniqueSocialInfoList.setSocialInfos(toSet);
        assertEquals(toSet, uniqueSocialInfoList.toSet());
        // Should work for list with existing social infos
        HashSet<SocialInfo> nextToSet = new HashSet<>(Arrays.asList(aliceFacebook, aliceTwitter));
        uniqueSocialInfoList.setSocialInfos(nextToSet);
        assertEquals(nextToSet, uniqueSocialInfoList.toSet());
    }

    @Test
    public void uniqueSocialInfoList_equals_success() throws UniqueSocialInfoList.DuplicateSocialTypeException {
        UniqueSocialInfoList aliceList = prepareUniqueSocialInfoList(aliceFacebook, aliceTwitter);
        UniqueSocialInfoList bobList = prepareUniqueSocialInfoList(bobFacebook, bobTwitter);
        assertFalse(aliceList.equals(bobList));
        UniqueSocialInfoList aliceListCopy = prepareUniqueSocialInfoList(aliceFacebook, aliceTwitter);
        assertTrue(aliceList.equals(aliceListCopy));

        UniqueSocialInfoList aliceListOrdered = new UniqueSocialInfoList();
        aliceListOrdered.add(aliceFacebook);
        aliceListOrdered.add(aliceTwitter);
        UniqueSocialInfoList aliceListReversed = new UniqueSocialInfoList();
        aliceListReversed.add(aliceTwitter);
        aliceListReversed.add(aliceFacebook);
        assertFalse(aliceListOrdered.equals(aliceListReversed));
    }

    @Test
    public void uniqueSocialInfoList_equalsOrderInsensitive_success() throws UniqueSocialInfoList.DuplicateSocialTypeException {
        UniqueSocialInfoList aliceList = prepareUniqueSocialInfoList(aliceFacebook, aliceTwitter);
        UniqueSocialInfoList bobList = prepareUniqueSocialInfoList(bobFacebook, bobTwitter);
        assertFalse(aliceList.equalsOrderInsensitive(bobList));
        UniqueSocialInfoList aliceListCopy = prepareUniqueSocialInfoList(aliceFacebook, aliceTwitter);
        assertTrue(aliceList.equalsOrderInsensitive(aliceListCopy));

        UniqueSocialInfoList aliceListOrdered = new UniqueSocialInfoList();
        aliceListOrdered.add(aliceFacebook);
        aliceListOrdered.add(aliceTwitter);
        UniqueSocialInfoList aliceListReversed = new UniqueSocialInfoList();
        aliceListReversed.add(aliceTwitter);
        aliceListReversed.add(aliceFacebook);
        assertTrue(aliceListOrdered.equalsOrderInsensitive(aliceListReversed));
    }

    private UniqueSocialInfoList prepareUniqueSocialInfoList(SocialInfo... socialInfos) {
        return new UniqueSocialInfoList(
                new HashSet<>(Arrays.asList(socialInfos))
        );
    }

    private static class MockSocialInfo extends SocialInfo {
        private String socialType;

        public MockSocialInfo(String username, String socialType) {
            super(username);
            this.socialType = socialType;
        }

        @Override
        public String getSocialType() {
            return socialType;
        }

        @Override
        public String getSocialUrl() {
            return null;
        }
    }
}
