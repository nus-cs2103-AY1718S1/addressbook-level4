package seedu.address.logic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

//@@author hanselblack
/**
 * To check for internet connection
 */
public class InternetConnectionCheck {

    private static final Logger logger = LogsCenter.getLogger(InternetConnectionCheck.class);

    /**
     * Returns true if there is internet connection to google.com
     */
    public static boolean isConnectedToInternet() {
        Socket sock = new Socket();
        InetSocketAddress addr = new InetSocketAddress("google.com", 80);
        try {
            sock.connect(addr, 3000);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                sock.close();
            } catch (IOException e) {
                logger.info("Unable to close socket");
            }
        }
    }
}
