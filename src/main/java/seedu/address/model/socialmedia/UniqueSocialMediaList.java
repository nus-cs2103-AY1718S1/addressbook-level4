package seedu.address.model.socialmedia;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Set;

import seedu.address.model.socialmedia.exceptions.DuplicateSocialMediaException;
import seedu.address.model.socialmedia.exceptions.SocialMediaNotFoundException;

/**
 * A list of socialMediaUrlss that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see SocialMedia#equals(Object)
 */


public class UniqueSocialMediaList {

    private HashMap<String, String> internalHashMap;

    /**
     * Creates a UniqueSocialMediaList using given SocialMedias.
     * Enforces no nulls.
     */
    public UniqueSocialMediaList(Set<SocialMedia> socialMediaUrls) {
        requireAllNonNull(socialMediaUrls);
        internalHashMap = new HashMap<>();
        for ( SocialMedia sm : socialMediaUrls) {
            if ( sm.getName().url.contains("facebook")) {
                internalHashMap.put("FB", sm.getName().url);
            } else if ( sm.getName().url.contains("twitter")) {
                internalHashMap.put("TW", sm.getName().url);
            } else if ( sm.getName().url.contains("instagram")) {
                internalHashMap.put("IN", sm.getName().url);
            } else if ( sm.getName().url.contains("plus.google.com")) {
                internalHashMap.put("GP", sm.getName().url);
            } else {
                throw new AssertionError("Invalid URL for social media");
            }
        }
    }

    /**
     * Constructs empty SocialMediaList.
     */
    public UniqueSocialMediaList() {}

    /**
     * Returns all socialMediaUrlss in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */

    /**
     * Returns true if the list contains an equivalent SocialMedia as the given argument.
     */
    public boolean contains(ReadOnlySocialMedia toCheck) {
        requireNonNull(toCheck);
        return internalHashMap.containsValue(toCheck.getName().url);
    }

    /**
     * Adds a SocialMedia to the list.
     * throws seedu.address.model.socialmedia.exceptions.DuplicateSocialMediaException
     * if the SocialMedia to add is a duplicate of an existing SocialMedia in the list.
     */
    public void add(ReadOnlySocialMedia sm) throws DuplicateSocialMediaException {
        requireNonNull(sm);
        if ( sm.getName().url.contains("facebook")) {
            internalHashMap.put("FB", sm.getName().url);
        } else if ( sm.getName().url.contains("twitter")) {
            internalHashMap.put("TW", sm.getName().url);
        } else if ( sm.getName().url.contains("instagram")) {
            internalHashMap.put("IN", sm.getName().url);
        } else if ( sm.getName().url.contains("plus.google.com")) {
            internalHashMap.put("GP", sm.getName().url);
        } else {
            throw new AssertionError("Invalid URL for social media");
        }
    }

    /**
     * Removes the equivalent socialMediaUrls from the hashmap.
     *
     * @throws SocialMediaNotFoundException if no such socialMediaUrls could be found in the list.
     */
    public boolean remove(ReadOnlySocialMedia sm) throws SocialMediaNotFoundException {
        requireNonNull(sm);
        String toFind = sm.getName().url;
        //Replace with default URLs
        if ( internalHashMap.containsValue(toFind)) {
            if (toFind.contains("facebook")) {
                internalHashMap.put("FB", "https://www.facebook.com");
                return true;
            } else if ( toFind.contains("twitter")) {
                internalHashMap.put("TW", "https://www.twitter.com");
                return true;
            } else if ( toFind.contains("instagram")) {
                internalHashMap.put("IN", "https://www.instagram.com");
                return true;
            } else if ( toFind.contains("plus.google.com")) {
                internalHashMap.put("GP", "https://www.plus.google.com");
                return true;
            } else {
                throw new AssertionError("Invalid URL for social media");
            }
        }
        //Should not reach here as last case throws an assertion error
        return false;
    }

}

