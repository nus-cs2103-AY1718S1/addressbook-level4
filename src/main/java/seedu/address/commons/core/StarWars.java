package seedu.address.commons.core;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.telnet.TelnetClient;

/**
 * A star wars object that includes the streamer and telnet client source of the stream.
 * Obviously doesn't work if their server is down.
 * @author towel.blinkenlights.nl
 */
public class StarWars {

    private static StarWars instance;
    private static final String server = "towel.blinkenlights.nl";

    private TelnetClient telnetClient = new TelnetClient();
    private InputStream in;

    private StarWars() {
        try {
            // Connect to the specified server
            telnetClient.connect(server, 23);

            // Get input stream reference
            in = telnetClient.getInputStream();

        } catch (Exception e) {
            // nothing needs to be done.
        }
    }

    public static StarWars getInstance() {
        if (instance == null) {
            instance = new StarWars();
        }
        return instance;
    }

    public static boolean hasInstance() {
        return instance == null;
    }

    /**
     * Disconnects the initialized telnet client on exit.
     */
    public static void shutDown() {
        try {
            if (instance != null) {
                getInstance().telnetClient.disconnect();
            }
        } catch (IOException e) {
            shutDown();
        }
    }

    public InputStream getIn() {
        return in;
    }
}
