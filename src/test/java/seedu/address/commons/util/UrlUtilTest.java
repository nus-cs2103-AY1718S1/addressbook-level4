package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_URL_COMMA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_URL;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UrlUtilTest {
    private ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseUrlString_success_checkCorrectness() throws Exception {
        URL url = UrlUtil.parseUrlString(VALID_URL);

        assertEquals("https", url.getProtocol());
        assertEquals("www.google.com.sg", url.getAuthority());
        assertEquals("/contacts", url.getPath());
        assertEquals("day=monday", url.getQuery());
    }

    @Test
    public void parseUrlString_fail_expectException() throws Exception {
        thrown.expect(MalformedURLException.class);
        UrlUtil.parseUrlString(INVALID_URL_COMMA);
    }

    @Test
    public void fetchUrlParameters_success_checkCorrectness() throws Exception {
        Map<String, String> parameters = UrlUtil.fetchUrlParameters(new URL(VALID_URL));

        assertEquals(1, parameters.size());
        assertTrue(parameters.containsKey("day"));
        assertTrue(parameters.containsValue("monday"));
    }

    @Test
    public void fetchUrlParameterKeys_success_checkCorrectness() throws Exception {
        Set<String> keys = UrlUtil.fetchUrlParameterKeys(new URL(VALID_URL));

        assertEquals(1, keys.size());
        assertTrue(keys.contains("day"));
    }
}
