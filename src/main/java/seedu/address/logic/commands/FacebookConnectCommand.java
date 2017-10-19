package seedu.address.logic.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Connects the addressbook to a personal Facebook account.
 */
public class FacebookConnectCommand extends Command {
    public static final String COMMAND_WORD = "facebook connect";
    public static final String COMMAND_ALIAS = "fbconnect";
    public static final String MESSAGE_SUCCESS = "Connected to your Facebook Account!";

    private static boolean authenticated = false;
    private static String domain = "https://www.facebook.com/";
    private static String appID = "131555220900267";
    private static String commaSeparetedPermissions = "user_about_me,email,publish_actions,user_birthday,"
            + "user_education_history,user_friends,user_games_activity,user_hometown,user_likes,"
            + "user_location,user_photos,user_posts,user_relationship_details,user_relationships,"
            + "user_religion_politics,user_status,user_tagged_places,user_videos,user_website,user_work_history,"
            + "ads_management,ads_read,business_management,manage_pages,pages_manage_cta,publish_pages,"
            + "user_actions.books,user_actions.fitness,pages_messaging,read_custom_friendlists,"
            + "pages_messaging_phone_number,pages_messaging_subscriptions,pages_show_list,user_actions.music,"
            + "user_actions.news,read_page_mailboxes,rsvp_event,user_events,user_managed_groups,"
            + "pages_manage_instant_articles,user_actions.video,instagram_basic,instagram_manage_comments,"
            + "instagram_manage_insights,read_audience_network_insights,read_insights";
    private static String authUrl = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id=" + appID
            + "&redirect_uri=" + domain + "&scope=" + commaSeparetedPermissions;
    private static String welcomeUser;
    private static Facebook facebookInstance;
    private String accessToken;

    /**
     * Returns the authenticated Facebook instance
     */
    public static Facebook getFacebookInstance() {
        return facebookInstance;
    }

    /**
     * Checks if there is an authenticated Facebook instance
     */
    public static boolean isAuthenticated() {
        return authenticated;
    }

    /*
    public static String getAuthUrl() {
        return authUrl;
    }
    */

    @Override
    public CommandResult execute() throws CommandException {

        System.setProperty("webdriver.chrome.driver", "chromedriver");

        WebDriver driver = new ChromeDriver();
        driver.get(authUrl);
        while (true) {
            if (driver.getCurrentUrl().contains("access_token")) {
                Pattern p = Pattern.compile("access_token=(.*?)\\&");
                String url = driver.getCurrentUrl();
                Matcher m = p.matcher(url);
                m.matches();
                m.find();
                accessToken = m.group(1);
                driver.quit();

                facebookInstance = new FacebookFactory().getInstance();
                facebookInstance.setOAuthPermissions(commaSeparetedPermissions);
                facebookInstance.setOAuthAccessToken(new AccessToken(accessToken, null));
                try {
                    welcomeUser = facebookInstance.getName();
                } catch (FacebookException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        if (accessToken != null) {
            authenticated = true;
            return new CommandResult(MESSAGE_SUCCESS + " User name: " + welcomeUser);
        } else {
            throw new CommandException("Error in Facebook Authorisation");
        }
    }
}
