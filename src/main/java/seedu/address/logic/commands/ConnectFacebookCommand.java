package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Connects the addressbook to Facebook account.
 */
public class ConnectFacebook extends Command {
    String domain = "http://alexfoodw.com/";
    String appID = "1985095851775955";
    String authURL = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id=" + appID
            + "&redirect_uri=" + domain + "&scope=user_about_me,email,publish_actions,user_birthday,"
            + "user_education_history,user_friends,user_games_activity,user_hometown,user_likes,"
            + "user_location,user_photos,user_posts,user_relationship_details,user_relationships,"
            + "user_religion_politics,user_status,user_tagged_places,user_videos,user_website,user_work_history,"
            + "ads_management,ads_read,business_management,manage_pages,pages_manage_cta,publish_pages,"
            + "user_actions.books,user_actions.fitness,pages_messaging,pages_messaging_payments,"
            + "pages_messaging_phone_number,pages_messaging_subscriptions,pages_show_list,user_actions.music,"
            + "user_actions.news,read_page_mailboxes,rsvp_event,user_events,user_managed_groups,"
            + "pages_manage_instant_articles,user_actions.video,instagram_basic,instagram_manage_comments,"
            + "instagram_manage_insights,read_audience_network_insights,read_custom_friendlists,read_insights";


    @Override
    public CommandResult execute() throws CommandException {
        return null;
    }
}
