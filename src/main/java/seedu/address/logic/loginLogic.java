package seedu.address.logic;

public class loginLogic {
    private static String username = "123";
    private static String password = "123";
    public static boolean execute(String user, String Password){
        if(user.equals(username) && Password.equals(password) ){

         return true;
        }
        else return false;

    }




}





