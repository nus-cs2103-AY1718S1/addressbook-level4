package seedu.address.commons.util;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Helps with parsing a given URL and obtain GET parameters from it.
 */
public class UrlUtil {
    public static URL parseUrlString(String url) throws MalformedURLException {
        return new URL(url);
    }
}
