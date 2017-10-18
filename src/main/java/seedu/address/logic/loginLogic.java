package seedu.address.logic;

import java.util.logging.Logger;
import seedu.address.commons.core.LogsCenter;


public class loginLogic {
    private static String username = "123";
    private static String password = "123";
    private final Logger logger = LogsCenter.getLogger(this.getClass());


    public loginLogic() {
    }

    public boolean execute(String user, String Password) {

        if (user.equals(username) && Password.equals(password)) {

                logger.info("login finished");


            return true;
        }
        return false;
    }


}





