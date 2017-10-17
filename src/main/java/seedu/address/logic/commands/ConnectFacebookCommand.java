package seedu.address.logic.commands;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.BrowserPanel;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sun.misc.Signal.raise;

/**
 * Connects the addressbook to Facebook account.
 */
public class ConnectFacebookCommand extends Command {
    public static final String COMMAND_WORD = "connectfacebook";
    public static final String COMMAND_ALIAS = "connectfb";

    public static final String MESSAGE_SUCCESS = "Connected to your Facebook Account!";

    private static String domain = "https://www.facebook.com/";
    private static String appID = "1985095851775955";
    private String accessToken;
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

    private static String authURL = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id=" + appID
            + "&redirect_uri=" + domain + "&scope=" + commaSeparetedPermissions;


    public static String getAuthURL() {
        return authURL;
    }

    @Override
    public CommandResult execute() throws CommandException {

        System.setProperty("webdriver.chrome.driver", "chromedriver");

        WebDriver driver = new ChromeDriver();
        driver.get(authURL);
        while(true){
            if(driver.getCurrentUrl().contains("facebook.com/?#access_token")) {
                Pattern p = Pattern.compile("access_token=(.*?)\\&");
                String url = driver.getCurrentUrl();
                Matcher m = p.matcher(url);
                m.matches();
                m.find();
                accessToken = m.group(1);
                driver.quit();

                Facebook facebook = new FacebookFactory().getInstance();
                facebook.setOAuthPermissions(commaSeparetedPermissions);
                facebook.setOAuthAccessToken(new AccessToken(accessToken, null));
                break;
            }
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
