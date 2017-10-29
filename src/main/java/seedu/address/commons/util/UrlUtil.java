package seedu.address.commons.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Strings;

/**
 * Helps with parsing a given URL and obtain GET parameters from it.
 */
public class UrlUtil {
    public static URL parseUrlString(String url) throws MalformedURLException {
        return new URL(url);
    }

    /**
     * Fetches all GET parameters from a given {@link URL} object. It is assumed that there is no GET parameter with
     * the same key in the {@code url}. If there is, only the last one (among those parameters with the same name)
     * will be included in the returned {@link Map}. See also {@link #fetchUrlParameterKeys(URL)}.
     *
     * @param url is a given {@link URL} object.
     *
     * @return a {@link Map} containing all key-value pairs of GET parameters in the given {@code url}.
     */
    public static Map<String, String> fetchUrlParameters(URL url) throws UnsupportedEncodingException {
        String query = urlDecode(url.getQuery());

        if (Strings.isNullOrEmpty(query)) {
            return Collections.emptyMap();
        }

        Map<String, String> pairs = new HashMap<>();
        for (String pair: query.split("&")) {
            int index = pair.indexOf("=");
            pairs.put(pair.substring(0, index), pair.substring(index + 1));
        }

        return pairs;
    }

    /**
     * Fetches the keys all GET parameters from a given {@link URL} object. It is assumed that there is no GET parameter
     * with the same key in the {@code url}. If there is, only the last one (among those parameters with the same name)
     * will be included in the returned {@link Set}. See also {@link #fetchUrlParameters(URL)}.
     *
     * @param url is a given {@link URL} object.
     *
     * @return a {@link Set} containing all keys of GET parameters in the given {@code url}.
     */
    public static Set<String> fetchUrlParameterKeys(URL url) throws UnsupportedEncodingException {
        String query = urlDecode(url.getQuery());

        if (Strings.isNullOrEmpty(query)) {
            return Collections.emptySet();
        }

        String[] pairs = query.split("&");
        return Arrays.stream(pairs).map(pair -> pair.substring(0, pair.indexOf("="))).collect(Collectors.toSet());
    }

    private static String urlDecode(String url) throws UnsupportedEncodingException {
        return URLDecoder.decode(url, "utf-8");
    }
}
