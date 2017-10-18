package seedu.address.logic;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * The Login Logic of the app.
 */
public class LoginLogic {
    private static String username = "123";
    private static String password = "123";
    private final Logger logger = LogsCenter.getLogger(this.getClass());


    public LoginLogic() {
    }

    /**
     *
     * @param user
     * @param password
     * @return
     */
    public boolean execute(String user, String password) {

        if (user.equals(this.username) && password.equals(this.password)) {

            logger.info("login finished");

            return true;
        }

        return false;
    }


}


